package tech.buildrun.agregadordeinvestimentos.service;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.buildrun.agregadordeinvestimentos.controller.dto.AccountResponseDto;
import tech.buildrun.agregadordeinvestimentos.controller.dto.CreateAccountDto;
import tech.buildrun.agregadordeinvestimentos.controller.dto.CreateUserDto;
import tech.buildrun.agregadordeinvestimentos.controller.dto.UpdateUserDto;
import tech.buildrun.agregadordeinvestimentos.entity.Account;
import tech.buildrun.agregadordeinvestimentos.entity.BillingAddress;
import tech.buildrun.agregadordeinvestimentos.entity.User;
import tech.buildrun.agregadordeinvestimentos.repository.AccountRepository;
import tech.buildrun.agregadordeinvestimentos.repository.BillingAddressRepository;
import tech.buildrun.agregadordeinvestimentos.repository.UserRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    private AccountRepository accountRepository;

    private BillingAddressRepository billingAddressRepository;

    private BillingAddressService billingAddressService;

    public UserService(UserRepository userRepository,  AccountRepository accountRepository, BillingAddressRepository billingAddressRepository,
                       BillingAddressService billingAddressService) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
        this.billingAddressService = billingAddressService;
    }



    public UUID createUser (CreateUserDto createUserDto) {
        // DTO -> ENTITY

        var entity = new User(
                null,
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                null,
                null
        );

        if (userRepository.existsByEmail(createUserDto.email())) {

            System.out.println("Email ja cadastrado no Banco de Dados!");
            return null;
        } else {

            var userSaved = userRepository.save(entity);

            return userSaved.getUserId();
        }

    }

    public Optional<User> getUserById (String UserId) {


        return userRepository.findById(UUID.fromString(UserId));
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }



    public void updateUserById (String userId,
                                UpdateUserDto updateUserDto) {

        var id = UUID.fromString(userId);
        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            var user =  userEntity.get();

            if (updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());
            }

            if (updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
        }
    }


    public void deleteById(String userId) {
        var id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        }

    }

    @Transactional
    public void createAccount (String userId, CreateAccountDto createAccountDto) {

        var user = userRepository.getReferenceById(UUID.fromString(userId));

                //.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var infoCep = billingAddressService.getResponseCep(createAccountDto.cep());

        if (infoCep.has("erro") && infoCep.get("erro").asBoolean()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        var accountEntity = new Account(
                null,
                createAccountDto.description(),
                user,
                null,
                new ArrayList<>()
        );

        var billingAddress = new BillingAddress(
                null,
                accountEntity,
                infoCep.path("logradouro").asString(),
                createAccountDto.number(),
                infoCep.path("bairro").asString()
        );

        accountEntity.setBillingAddress(billingAddress);

        accountRepository.save(accountEntity);
    }

    public List<AccountResponseDto> listAccounts(String userId) {

        var user = userRepository.findById(UUID.fromString(userId)).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return user.getAccounts().stream().map(ac -> new AccountResponseDto(ac.getAccountId().toString(),
                ac.getDescription(), user.getUserId())).toList();
    }
}

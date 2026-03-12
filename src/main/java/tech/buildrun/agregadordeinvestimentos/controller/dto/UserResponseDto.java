package tech.buildrun.agregadordeinvestimentos.controller.dto;

import tech.buildrun.agregadordeinvestimentos.entity.Account;

import java.util.List;
import java.util.UUID;

public record UserResponseDto(UUID userId, String username, String email, List<Account> accounts) {
}

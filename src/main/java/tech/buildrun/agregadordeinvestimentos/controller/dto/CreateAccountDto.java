package tech.buildrun.agregadordeinvestimentos.controller.dto;

import tech.buildrun.agregadordeinvestimentos.entity.BillingAddress;
import tech.buildrun.agregadordeinvestimentos.entity.User;

import java.util.UUID;

public record CreateAccountDto(UUID accountId, String description, String cep, String number) {
}

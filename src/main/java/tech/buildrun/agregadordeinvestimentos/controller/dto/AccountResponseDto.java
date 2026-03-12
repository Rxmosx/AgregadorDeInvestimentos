package tech.buildrun.agregadordeinvestimentos.controller.dto;

import java.util.UUID;

public record AccountResponseDto(String accountId, String description, UUID userId) {
}

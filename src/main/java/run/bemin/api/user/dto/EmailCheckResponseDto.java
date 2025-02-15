package run.bemin.api.user.dto;

public record EmailCheckResponseDto(boolean isDuplicate, String message) {}
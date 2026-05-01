package roomescape.controller.dto;

public record CommonErrorResponse(
        String reasonOfError
) {
    public static CommonErrorResponse fromException(RuntimeException exception) {
        return new CommonErrorResponse(exception.getMessage());
    }
}

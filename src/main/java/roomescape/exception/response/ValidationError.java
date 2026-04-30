package roomescape.exception.response;

public record ValidationError(
        String field,
        String reason
) {
    public static ValidationError of(String field, String reason) {
        return new ValidationError(field, reason);
    }
}
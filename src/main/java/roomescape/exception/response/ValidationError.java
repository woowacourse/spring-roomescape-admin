package roomescape.exception.response;

public record ValidationError(
        String field,
        String message
) {
    public static ValidationError of(String field, String message) {
        return new ValidationError(field, message);
    }
}
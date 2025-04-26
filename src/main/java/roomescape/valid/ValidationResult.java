package roomescape.valid;

import java.util.Map;

public record ValidationResult(boolean isValid, Map<String, Map<String, String>> errorResponse) {

    public static ValidationResult valid() {
        return new ValidationResult(true, null);
    }

    public static ValidationResult invalid(final Map<String, Map<String, String>> errorResponse) {
        return new ValidationResult(false, errorResponse);
    }
}

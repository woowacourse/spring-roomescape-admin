package roomescape.valid;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import roomescape.valid.annotation.NotNull;

public class ValidationUtils {

    public static ValidationResult validate(Object object) {
        Map<String, String> fieldErrors = validateFields(object);
        if (!fieldErrors.isEmpty()) {
            Map<String, Map<String, String>> errorResponse = Map.of("fieldErrors", fieldErrors);
            return ValidationResult.invalid(errorResponse);
        }
        return ValidationResult.valid();
    }

    private static Map<String, String> validateFields(Object object) {
        Map<String, String> validationErrors = new HashMap<>();

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(NotNull.class)) {
                try {
                    Object value = field.get(object);
                    if (value == null) {
                        NotNull fieldAnnotation = field.getAnnotation(NotNull.class);
                        validationErrors.put(field.getName(), fieldAnnotation.message());
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return validationErrors;
    }
}

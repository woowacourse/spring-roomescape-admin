package roomescape.validation.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import roomescape.validation.exception.RequestValidationException;

import java.util.List;

public class SpringCustomValidator implements Validator {

    private final FieldBlankValidator blankValidator = new FieldBlankValidator();

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        List<String> blankErrors = blankValidator.validate(target);
        if (!blankErrors.isEmpty()) {
            throw new RequestValidationException(blankErrors.getFirst());
        }
    }

}

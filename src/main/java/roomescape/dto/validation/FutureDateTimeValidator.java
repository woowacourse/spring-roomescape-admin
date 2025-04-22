package roomescape.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import roomescape.dto.AddReservationDto;

public class FutureDateTimeValidator implements ConstraintValidator<FutureDateTime, AddReservationDto> {

    @Override
    public boolean isValid(AddReservationDto dto, ConstraintValidatorContext context) {
        if (dto.date() == null) {
            return false;
        }

        LocalDate today = LocalDate.now();
        LocalDate date = dto.date();
        return date.isEqual(today) || date.isAfter(today);
    }
}

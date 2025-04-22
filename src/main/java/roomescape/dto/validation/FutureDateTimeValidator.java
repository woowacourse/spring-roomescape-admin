package roomescape.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import roomescape.dto.AddReservationDto;

public class FutureDateTimeValidator implements ConstraintValidator<FutureDateTime, AddReservationDto> {

    @Override
    public boolean isValid(AddReservationDto dto, ConstraintValidatorContext context) {
        if (dto.date() == null || dto.time() == null) {
            return false;
        }

        LocalDateTime reservationDateTime = LocalDateTime.of(dto.date(), dto.time());
        LocalDateTime now = LocalDateTime.now();

        return reservationDateTime.isAfter(now);
    }
}

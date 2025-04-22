package roomescape.dto.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public class ReservationTimeValidator implements ConstraintValidator<ValidReservationTime, ReservationTime> {

    @Override
    public boolean isValid(ReservationTime reservationTime, ConstraintValidatorContext context) {
        return reservationTime.getTime().isAfter(LocalTime.now());
    }
}

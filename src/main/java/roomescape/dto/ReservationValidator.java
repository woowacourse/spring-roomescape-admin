package roomescape.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public class ReservationValidator implements ConstraintValidator<ValidReservation, Reservation> {

    @Override
    public boolean isValid(Reservation reservation, ConstraintValidatorContext context) {
        if (isNameEmpty(reservation)) {
            return false;
        }

        LocalDate today = LocalDate.now();
        LocalTime time = reservation.getTime();
        LocalDate date = reservation.getDate();
        LocalTime currenTime = LocalTime.now();

        if (isPast(date, today) || isPastTodayTime(date, today, time, currenTime)) {
            return false;
        }
        return true;
    }

    private boolean isNameEmpty(Reservation reservation) {
        return reservation.getName().isBlank() || reservation.getName() == null;
    }

    private boolean isPast(LocalDate date, LocalDate today) {
        return date.isBefore(today);
    }

    private boolean isPastTodayTime(LocalDate date, LocalDate today, LocalTime time, LocalTime currenTime) {
        return date.isEqual(today) && time.isBefore(currenTime);
    }
}

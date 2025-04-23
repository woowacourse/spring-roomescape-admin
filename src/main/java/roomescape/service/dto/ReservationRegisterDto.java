package roomescape.service.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationRegisterDto(
        @FutureOrPresent
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotBlank
        @Length(min = 1, max = 4)
        String name,

        @NotNull
        Long timeId
) {
    public Reservation toReservation(ReservationTime reservationTime) {
        return new Reservation(name, date, reservationTime);
    }
}

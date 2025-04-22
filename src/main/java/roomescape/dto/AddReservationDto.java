package roomescape.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.validation.FutureDateTime;

@FutureDateTime
public record AddReservationDto(@NotNull @NotBlank(message = "이름이 비어있을 수 없습니다.") String name,
                                @FutureOrPresent(message = "날짜는 현재보다 과거여야합니다.") LocalDate date, LocalTime time) {

    public Reservation toReservation(Long reservationTimeId) {
        return new Reservation(null, name, date, new ReservationTime(reservationTimeId, time));
    }

    public ReservationTime toReservationTime() {
        return new ReservationTime(null, time);
    }
}


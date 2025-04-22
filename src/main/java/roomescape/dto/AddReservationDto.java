package roomescape.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record AddReservationDto(@NotNull @NotBlank(message = "이름이 비어있을 수 없습니다.") String name,
                                @FutureOrPresent(message = "날짜는 현재보다 과거여야합니다.") LocalDate date, LocalTime time) {

    public Reservation toEntity() {
        return new Reservation(null, name,
                date, time);
    }
}


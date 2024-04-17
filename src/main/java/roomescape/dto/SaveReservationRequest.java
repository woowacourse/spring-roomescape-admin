package roomescape.dto;

import roomescape.domain.ClientName;
import roomescape.entity.ReservationEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record SaveReservationRequest(LocalDate date, String name, LocalTime time) {
    public ReservationEntity toEntity() {
        return ReservationEntity.of(
                new ClientName(name),
                LocalDateTime.of(date, time)
        );
    }
}

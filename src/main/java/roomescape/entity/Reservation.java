package roomescape.entity;

import roomescape.dto.ReservationCreateRequest;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(
        Long id,
        String name,
        LocalDate date,
        LocalTime time
) {
    public static Reservation from(Long id, ReservationCreateRequest request) {
        return new Reservation(id, request.name(), request.date(), request.time());
    }
}

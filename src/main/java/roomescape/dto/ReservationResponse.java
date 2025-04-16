package roomescape.dto;

import roomescape.Reservation;
import roomescape.entity.ReservationEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ReservationResponse(long id,
                                  String name,
                                  LocalDate date,
                                  LocalTime time) {
    public static ReservationResponse from(ReservationEntity reservationEntity) {
        return new ReservationResponse(reservationEntity.getId(), reservationEntity.getName(), reservationEntity.getDate(), reservationEntity.getTime());
    }

    public static List<ReservationResponse> from(List<ReservationEntity> reservations) {
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }
}

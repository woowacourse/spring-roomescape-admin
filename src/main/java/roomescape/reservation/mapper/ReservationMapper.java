package roomescape.reservation.mapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;

public class ReservationMapper {

    private ReservationMapper() {}

    public static Reservation toEntity(ReservationRequest reservationRequest) {
        LocalDate date = LocalDate.parse(reservationRequest.date(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalTime time = LocalTime.parse(reservationRequest.time(), DateTimeFormatter.ofPattern("HH:mm"));
        return Reservation.builder()
                .name(reservationRequest.name())
                .date(date)
                .time(time)
                .build();
    }

    public static ReservationResponse toResponse(Reservation reservation) {
        return ReservationResponse.builder()
                .id(reservation.getId())
                .build();
    }
}

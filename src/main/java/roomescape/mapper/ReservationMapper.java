package roomescape.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.model.Reservation;

public class ReservationMapper {
    public static Reservation toDomain(ReservationRequest request) {
        LocalDate date = LocalDate.parse(request.date());
        LocalTime time = LocalTime.parse(request.time());
        LocalDateTime reservationTime = LocalDateTime.of(date, time);

        return Reservation.createReservationWithoutID(request.name(), reservationTime);
    }

    private static final DateTimeFormatter TIME_FORMATTER = java.time.format.DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static ReservationResponse toDto(Reservation reservation) {
        LocalDateTime dateTime = reservation.getReservationTime();
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                dateTime.toLocalDate().format(DATE_FORMATTER),
                dateTime.toLocalTime().format(TIME_FORMATTER));
    }

    public static List<ReservationResponse> toDtos(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationMapper::toDto)
                .toList();
    }
}

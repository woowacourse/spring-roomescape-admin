package roomescape.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.dto.response.TimeResponse;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

public class ReservationMapper {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static Reservation toDomain(ReservationRequest request, ReservationTime reservationTime) {
        LocalDate date = LocalDate.parse(request.date());
        return Reservation.withoutId(request.name(), date, reservationTime);
    }

    public static ReservationResponse toDto(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getReservationDate().format(DATE_FORMATTER),
                TimeResponse.toDto(reservation.getReservationTime())
        );
    }

    public static List<ReservationResponse> toDtos(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationMapper::toDto)
                .toList();
    }
}

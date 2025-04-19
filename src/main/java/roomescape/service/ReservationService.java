package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.database.ReservationDatabase;
import roomescape.database.ReservationDatabaseImpl;
import roomescape.domain.Reservation;
import roomescape.domain.dto.ReservationReqDto;
import roomescape.domain.dto.ReservationResDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationDatabase reservationDatabase;

    public ReservationService() {
        this.reservationDatabase = new ReservationDatabaseImpl();
    }

    public List<ReservationResDto> readAll() {
        List<Reservation> reservations = reservationDatabase.findAll();
        return reservations.stream()
                .map(this::convertReservationResDto)
                .collect(Collectors.toList());
    }

    public ReservationResDto add(ReservationReqDto dto) {
        Reservation reservation = convertReservation(dto);
        Reservation savedReservation = reservationDatabase.add(reservation);
        return convertReservationResDto(savedReservation);
    }

    public void delete(Long id) {
        Reservation reservation = reservationDatabase.findById(id);
        reservationDatabase.delete(reservation);
    }

    private Reservation convertReservation(ReservationReqDto dto) {
        return Reservation.of(
                dto.getName(),
                dto.getDate(),
                dto.getTime());
    }

    private ReservationResDto convertReservationResDto(Reservation reservation) {
        return new ReservationResDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}

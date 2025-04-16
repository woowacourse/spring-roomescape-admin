package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.database.ReservationDatabase;
import roomescape.database.ReservationDatabaseImpl;
import roomescape.domain.Reservation;
import roomescape.domain.dto.ReservationRequestDto;

@Service
public class RoomEscapeService {

    private final ReservationDatabase reservationDatabase;

    public RoomEscapeService() {
        this.reservationDatabase = new ReservationDatabaseImpl();
    }

    public List<Reservation> readAll() {
        return reservationDatabase.findAll();
    }

    public Reservation add(ReservationRequestDto reservationDto) {
        Reservation reservation = convertReservation(reservationDto);
        return reservationDatabase.add(reservation);
    }

    public void delete(Long id) {
        Reservation reservation = reservationDatabase.findById(id);
        reservationDatabase.delete(reservation);
    }

    private Reservation convertReservation(ReservationRequestDto reservationDto) {
        return new Reservation(
                reservationDto.name(),
                reservationDto.date(),
                reservationDto.time());
    }
}

package roomescape.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationResponseDto;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.domain.Reservations;

@Service
public class ReservationService {

    private Reservations reservations;
    private final ReservationDao reservationDao;
    private final AtomicLong index = new AtomicLong(1);

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponseDto> getAllReservations() {
        List<Reservation> reservations = reservationDao.getAll();
        return reservations.stream()
                .map(ReservationResponseDto::new)
                .toList();
    }

    public ReservationResponseDto addReservation(ReservationRequestDto reservationRequestDto) {
        Reservation reservation = new Reservation(
                index.getAndIncrement(), reservationRequestDto.name(), reservationRequestDto.date(), reservationRequestDto.time()
        );
        reservations.add(reservation);
        return new ReservationResponseDto(reservation);
    }

    public void deleteReservation(long id) {
        reservations.delete(id);
    }
}

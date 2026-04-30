package roomescape.reservation.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.exception.ReservationErrorCode;
import roomescape.reservation.exception.ReservationException;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.time.entity.ReservationTime;
import roomescape.time.service.ReservationTimeService;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeService reservationTimeService;

    @Transactional
    public Reservation save(String name, LocalDate date, long timeId) {
        ReservationTime reservationTime = reservationTimeService.getById(timeId);
        Reservation nonIdReservation = Reservation.createNew(name, date, reservationTime);

        return reservationRepository.save(nonIdReservation);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Transactional
    public void deleteById(long id) {
        reservationRepository.deleteById(id);
    }

    public Reservation getById(long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.RESERVATION_NOT_FOUND));
    }

}

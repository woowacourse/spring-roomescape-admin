package roomescape.reservation.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roomescape.reservation.reservation.ReservationRepository;
import roomescape.time.service.ReservationTimeService;
import roomescape.exception.DataNotFoundException;
import roomescape.reservation.domain.Reservation;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeService reservationTimeService;

    public ReservationService(
            @Qualifier("h2ReservationRepository") final ReservationRepository reservationRepository,
            final ReservationTimeService reservationTimeService
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeService = reservationTimeService;
    }

    public Long save(final String name, final LocalDate date, final Long time_id) {
        final Reservation reservation = new Reservation(name, date, reservationTimeService.getById(time_id));

        return reservationRepository.save(reservation);
    }

    public Reservation getById(final Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("해당 예약 데이터가 존재하지 않습니다. id = " + id));
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public void deleteById(final Long id) {
        final Optional<Reservation> found = reservationRepository.findById(id);

        if (found.isEmpty()) {
            throw new DataNotFoundException("해당 예약 데이터가 존재하지 않습니다. id = " + id);
        }
        reservationRepository.delete(found.get());
    }
}

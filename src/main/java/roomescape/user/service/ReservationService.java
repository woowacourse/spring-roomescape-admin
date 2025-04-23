package roomescape.user.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roomescape.admin.repository.time.ReservationTimeRepository;
import roomescape.user.domain.Reservation;
import roomescape.user.repository.reservation.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(
            @Qualifier("h2ReservationRepository") final ReservationRepository reservationRepository,
            final ReservationTimeRepository reservationTimeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public Long save(final String name, final LocalDate date, final LocalTime time) {
        final Reservation reservation = new Reservation(name, date, reservationTimeRepository.getOneByStartAt(time));

        return reservationRepository.save(reservation);
    }

    public Reservation getOneById(final Long id) {
        return reservationRepository.getOneById(id);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public void deleteById(final Long id) {
        final Reservation found = reservationRepository.getOneById(id);

        reservationRepository.delete(found);
    }
}

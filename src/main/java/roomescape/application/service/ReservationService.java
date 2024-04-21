package roomescape.application.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.application.request.CreateReservationRequest;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.domain.ReservationTime;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Reservation reserve(CreateReservationRequest request) {
        Name name = new Name(request.name());
        LocalDate date = request.date();
        ReservationTime time = new ReservationTime(request.timeId());
        Reservation reservation = new Reservation(name, date, time);

        return reservationRepository.saveOne(reservation);
    }

    @Transactional
    public void deleteBy(Long id) {
        reservationRepository.deleteBy(id);
    }

    public List<Reservation> findReservations() {
        return reservationRepository.findAll();
    }
}

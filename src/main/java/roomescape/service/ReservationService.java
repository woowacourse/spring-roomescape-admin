package roomescape.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeRepository;
import roomescape.service.request.CreateReservationRequest;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository timeRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            ReservationTimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    @Transactional
    public Reservation reserve(CreateReservationRequest request) {
        Name name = new Name(request.name());
        LocalDate date = request.date();
        ReservationTime time = timeRepository.findById(request.timeId());
        Reservation newReservation = new Reservation(name, date, time);

        return reservationRepository.save(newReservation);
    }

    @Transactional
    public void deleteBy(long id) {
        reservationRepository.deleteBy(id);
    }

    public List<Reservation> findReservations() {
        return reservationRepository.findAll();
    }
}

package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            ReservationTimeRepository reservationTimeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public Reservation create(String nameValue, String dateValue, Long timeId) {
        Name name = new Name(nameValue);
        ReservationDate date = ReservationDate.from(dateValue);
        ReservationTime time = reservationTimeRepository.findById(timeId);
        Reservation reservation = new Reservation(null, name, date, time);

        return reservationRepository.save(reservation);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }
}

package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.domain.ReservationTimeRepository;
import roomescape.dto.app.ReservationAppRequest;

@Service
@Transactional
public class ReservationService {

    @Autowired
    private final ReservationRepository reservationRepository;
    @Autowired
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }


    public Reservation save(ReservationAppRequest request) {
        Reservation newReservation = new Reservation(request.name(), request.date(),
                reservationTimeRepository.findById(request.timeId()));

        return reservationRepository.save(newReservation);
    }

    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }
}

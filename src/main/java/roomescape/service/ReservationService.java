package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.model.ReservationInfo;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.model.Reservation;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAllReservationInfos()
                .stream()
                .map(this::generateReservationByInfo)
                .toList();
    }

    public Reservation createReservation(final ReservationInfo reservationInfo) {
        return generateReservationByInfo(reservationRepository.createReservation(reservationInfo));
    }

    private Reservation generateReservationByInfo(final ReservationInfo reservationInfo) {
        return reservationInfo.toReservation(
                reservationTimeRepository.findReservationTimeById(reservationInfo.timeId()));
    }

    public void deleteReservation(final Long id) {
        reservationRepository.deleteReservation(id);
    }

}

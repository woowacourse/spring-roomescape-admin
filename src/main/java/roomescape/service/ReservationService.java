package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.model.ReservationInfo;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationInfo> findAllReservations() {
        return reservationRepository.findAllReservationInfos()
                .stream()
                .map(this::generateReservationInfo)
                .toList();
    }

    public ReservationInfo createReservation(final Reservation reservation) {
        return generateReservationInfo(reservationRepository.createReservation(reservation));
    }

    private ReservationInfo generateReservationInfo(final Reservation reservation) {
        return reservation.toReservationInfo(
                reservationTimeRepository.findReservationTimeById(reservation.timeId()));
    }

    public void deleteReservation(final Long id) {
        reservationRepository.deleteReservation(id);
    }

}

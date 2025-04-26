package roomescape.user.reservation.domain;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.user.reservationtime.domain.ReservationTime;
import roomescape.user.reservationtime.domain.ReservationTimeRepository;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    @Transactional(readOnly = true)
    public Map<Reservation, ReservationTime> findReservationsWithTimes() {
        final List<Reservation> reservations = reservationRepository.findAll();

        return reservations.stream()
                .collect(Collectors.toMap(
                        reservation -> reservation,
                        reservation -> reservationTimeRepository.findById(reservation.getTimeId())
                                .orElseThrow(() -> new IllegalStateException("ReservationTime not found"))
                ));
    }

    @Transactional(readOnly = true)
    public Map.Entry<Reservation, ReservationTime> findReservationWithTime(final Long reservationId) {
        final Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalStateException("Reservation not found"));
        final ReservationTime reservationTime = reservationTimeRepository.findById(reservation.getTimeId())
                .orElseThrow(() -> new IllegalStateException("ReservationTime not found"));

        return Map.entry(reservation, reservationTime);
    }

    @Transactional
    public Long saveReservationWithTime(final Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Transactional
    public void deleteReservation(final Long id) {
        reservationRepository.deleteById(id);
    }
}

package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    private static final int MAX_RESERVATION_PER_TIME = 4;

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(Reservation reservation) {
        List<Reservation> reservationsInSameDateTime = reservationRepository.findAllByDateAndTime(reservation.getDate(), reservation.getTime());
        if (reservationsInSameDateTime.size() >= MAX_RESERVATION_PER_TIME) {
            throw new IllegalArgumentException("해당 시간대에 예약이 모두 찼습니다. (최대 4팀)");
        }
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        var reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 예약이 없습니다."));
        reservationRepository.deleteById(reservation.getId());
    }
}

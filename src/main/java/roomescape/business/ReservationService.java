package roomescape.business;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;
import roomescape.persistence.ReservationRepository;
import roomescape.persistence.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationService {
    private static final int MAX_RESERVATIONS_PER_TIME = 4;

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationResponse> getReservations() {
        var reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public Long createReservation(Reservation reservation) {
        var reservationTime = reservationTimeRepository.findById(reservation.getReservationTimeId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 예약 시간이 없습니다."));
        var reservationsInSameDateTime = reservationRepository.findAllByDateAndTime(reservation.getDate(), reservationTime);

        validateDuplicatedReservation(reservationsInSameDateTime, reservation);
        validateMaxReservationsPerTime(reservationsInSameDateTime);

        var savedReservation = reservationRepository.save(reservation);
        return savedReservation.getId();
    }

    private void validateDuplicatedReservation(List<Reservation> reservationsInSameDateTime, Reservation reservation) {
        boolean existingSameUser = reservationsInSameDateTime.stream()
                .anyMatch(existingReservation -> existingReservation.hasSameName(reservation));
        if (existingSameUser) {
            throw new IllegalArgumentException("동일한 시간에 같은 사용자가 예약할 수 없습니다.");
        }
    }

    private void validateMaxReservationsPerTime(List<Reservation> reservationsInSameDateTime) {
        if (reservationsInSameDateTime.size() >= MAX_RESERVATIONS_PER_TIME) {
            throw new IllegalArgumentException("해당 시간대에 예약이 모두 찼습니다. (최대 4팀)");
        }
    }

    public void deleteReservation(Long id) {
        var reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 예약이 없습니다."));
        reservationRepository.deleteById(reservation.getId());
    }
}

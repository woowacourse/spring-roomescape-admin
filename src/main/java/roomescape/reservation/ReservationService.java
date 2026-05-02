package roomescape.reservation;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservationtime.ReservationTime;
import roomescape.reservationtime.ReservationTimeNotFoundException;
import roomescape.reservationtime.ReservationTimeRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationResponseDTO createReservation(ReservationRequestDTO reservationRequestDTO) {
        ReservationTime reservationTime = reservationTimeRepository.findById(reservationRequestDTO.getTimeId())
                .orElseThrow(() -> new ReservationTimeNotFoundException("예약 시간을 찾을 수 없습니다."));
        Reservation reservation = new Reservation(
                reservationRequestDTO.getName(),
                reservationRequestDTO.getDate(),
                reservationTime);
        Long id = reservationRepository.insert(reservation);
        return new ReservationResponseDTO(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getReservationTime()
        );
    }

    public List<ReservationResponseDTO> findReservations() {
        return reservationRepository.findAllReservations().stream()
                .map(reservation -> new ReservationResponseDTO(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        reservation.getReservationTime()
                )).toList();
    }

    public void deleteReservation(Long id) {
        reservationRepository.delete(id);
    }
}

package roomescape.reservation.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.infra.ReservationRepository;
import roomescape.reservation.presentation.dto.request.ReservationSaveRequest;
import roomescape.reservation.presentation.dto.response.ReservationFindResponse;
import roomescape.reservation.presentation.dto.response.ReservationSaveResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationSaveResponse saveReservation(ReservationSaveRequest body) {
        Reservation reservation = reservationRepository.save(body.name(), body.date(), body.time());

        return new ReservationSaveResponse(reservation.getId(), reservation.getName(), reservation.getDate(),
                reservation.getTime());
    }

    public List<ReservationFindResponse> findAllReservations() {
        return reservationRepository.findAll().stream()
                .map(reservation -> new ReservationFindResponse(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        reservation.getTime()
                ))
                .toList();
    }

    /**
     * TODO: 삭제 실패시 예외처리
     */
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}

package roomescape.reservation.application;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationRepository;
import roomescape.reservation.ui.dto.ReservationRequest;
import roomescape.reservation.ui.dto.ReservationResponse;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DefaultReservationService implements ReservationService {

    private final ReservationRepository reservationRepository;

    public DefaultReservationService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<ReservationResponse> getReservations() {
        return ReservationResponse.from(reservationRepository.findAll());
    }

    @Override
    public ReservationResponse createReservation(final ReservationRequest reservationRequest) {
        return ReservationResponse.from(saved);
        final Reservation saved = reservationRepository.save(reservationRequestDto.toDomain());
    }

    @Override
    public void delete(final long id) {
        reservationRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        try {
            reservationRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new IllegalStateException("삭제에 실패했습니다");
        }
    }
}

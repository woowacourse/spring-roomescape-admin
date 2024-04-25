package roomescape.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.domain.ReservationTimeRepository;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @Transactional
    public ReservationResponse createReservation(ReservationRequest createDto) {
        validateReservationTime(createDto.timeId());
        Reservation reservation = createDto.toDomain();
        Reservation createdReservation = reservationRepository.create(reservation);
        return ReservationResponse.from(createdReservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.removeById(id);
    }

    private void validateReservationTime(Long id) {
        try {
            reservationTimeRepository.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException("해당되는 예약 시간이 없습니다.");
        }
    }
}

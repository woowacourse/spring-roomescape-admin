package roomescape.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.entity.ReservationTime;
import roomescape.domain.repository.ReservationRepository;
import roomescape.domain.entity.Reservation;
import roomescape.domain.dto.ReservationRequest;
import roomescape.domain.dto.ReservationResponse;
import roomescape.domain.repository.ReservationTimeRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    @Transactional
    public ReservationResponse save(ReservationRequest reservationRequest) {
        LocalDate date = reservationRequest.date();

        validateDuplicateReservation(date, reservationRequest.timeId());

        ReservationTime reservationTime = reservationTimeRepository.getById(reservationRequest.timeId());
        Reservation reservation = reservationRequest.toEntity(reservationTime);

        Long id = reservationRepository.save(reservation);
        Reservation savedReservation = Reservation.create(id, reservationRequest.name(), date, reservationTime);

        return ReservationResponse.from(savedReservation);
    }

    public List<ReservationResponse> getAll() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @Transactional
    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }

    private void validateDuplicateReservation(LocalDate date, Long timeId) {
        ReservationTime time = reservationTimeRepository.getById(timeId);

        if (reservationRepository.existsByDateAndTime(date, time)) {
            throw new IllegalArgumentException("예약이 마감된 일시입니다.");
        }
    }
}

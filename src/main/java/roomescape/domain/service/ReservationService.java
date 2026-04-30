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
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    @Transactional
    public ReservationResponse save(ReservationRequest reservationRequest) {
        List<Reservation> reservations = reservationRepository.getAll();
        for (Reservation reservation : reservations) {
            validateDuplicateReservation(reservation, reservationRequest.date(), reservationRequest.timeId());
        }

        ReservationTime reservationTime = reservationTimeRepository.getById(reservationRequest.timeId());
        Reservation reservation = reservationRequest.toEntity(null, reservationTime);

        reservation.setId(reservationRepository.save(reservation));

        return ReservationResponse.from(reservation);
    }

    public List<ReservationResponse> getAll() {
        return reservationRepository.getAll()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @Transactional
    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }

    private void validateDuplicateReservation(Reservation reservation, LocalDate date, Long timeId) {
        if (reservation.isSateDate(date) && Objects.equals(reservation.getTime().getId(), timeId)) {
            throw new IllegalArgumentException("예약이 마감된 일시입니다.");
        }
    }
}

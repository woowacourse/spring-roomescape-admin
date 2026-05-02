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
        LocalDate date = reservationRequest.date();

        List<Reservation> reservations = reservationRepository.findAll();
        for (Reservation reservation : reservations) {
            validateDuplicateReservation(reservation, date, reservationRequest.timeId());
        }

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
        Reservation reservation = reservationRepository.getById(id);
        ReservationTime reservationTime = reservation.getTime();

        reservationRepository.deleteById(id);
        reservationTimeRepository.deleteById(reservationTime.getId());
    }

    private void validateDuplicateReservation(Reservation reservation, LocalDate date, Long timeId) {
        if (reservation.isSameDate(date) && Objects.equals(reservation.getTime().getId(), timeId)) {
            throw new IllegalArgumentException("예약이 마감된 일시입니다.");
        }
    }
}

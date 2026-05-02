package roomescape.service;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import roomescape.domain.DuplicateEntityException;
import roomescape.domain.EntityNotFoundException;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.command.ReservationCommand;
import roomescape.service.result.ReservationResult;

@Service
@Transactional(readOnly = true)
@Validated
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    @Transactional
    public ReservationResult reserve(@NotNull(message = "예약 정보가 비어있습니다.") ReservationCommand request) {
        ReservationTime time = findTimeWithThrow(request.timeId());
        validateAlreadyReservation(request.date(), request.timeId(), time);

        Reservation reservation = new Reservation(request.name(), request.date(), time);
        Reservation saved = reservationRepository.save(reservation);
        return ReservationResult.from(saved);
    }

    public List<ReservationResult> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResult::from)
                .toList();
    }

    @Transactional
    public void cancelAllReservation(long id) {
        reservationRepository.delete(id);
    }

    private void validateAlreadyReservation(LocalDate date, long timeId, ReservationTime time) {
        if (reservationRepository.existByDateAndTimeId(date, timeId)) {
            throw new DuplicateEntityException("이미 예약 된 날짜입니다. (%s-%s)", date, time.getStartAt());
        }
    }

    private ReservationTime findTimeWithThrow(long timeId) {
        return reservationTimeRepository.findById(timeId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 시간 정보입니다."));
    }
}

package roomescape.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.domain.Theme;
import roomescape.exception.BadRequestException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.repository.ThemeRepository;
import roomescape.service.dto.ReservationRequest;
import roomescape.service.dto.ReservationResponse;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository timeRepository;
    private final ThemeRepository themeRepository;

    //TODO: 중복 예약 검증 방법이 이게 최선인지 고민해보기
    @Transactional
    public ReservationResponse create(ReservationRequest reservationRequest) {
        ReservationTime time = timeRepository.findById(reservationRequest.timeId()).orElseThrow();
        Theme theme = themeRepository.findById(reservationRequest.themeId()).orElseThrow();
        Reservation reservation = new Reservation(reservationRequest.name(), reservationRequest.date(), time, theme);
        validateIsBeforeNow(reservation);
        try {
            Reservation savedReservation = reservationRepository.save(reservation);
            return new ReservationResponse(savedReservation);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("같은 시간에 이미 예약이 존재합니다.");
        }
    }

    private void validateIsBeforeNow(Reservation reservation) {
        if (reservation.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("현재 시각보다 이전의 예약은 불가능합니다.");
        }
    }

    public ReservationResponse findOne(long id) {
        Reservation found = reservationRepository.findById(id).orElseThrow();

        return new ReservationResponse(found);
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::new)
                .toList();
    }

    @Transactional
    public void remove(Long id) {
        reservationRepository.delete(id);
    }
}

package roomescape.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationTime;
import roomescape.exception.BadRequestException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.dto.AvailableReservationTimeResponse;
import roomescape.service.dto.ReservationTimeRequest;
import roomescape.service.dto.ReservationTimeResponse;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public ReservationTimeResponse create(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeRequest.toTime();
        ReservationTime saved = reservationTimeRepository.save(reservationTime);

        return new ReservationTimeResponse(saved);
    }

    public ReservationTimeResponse findOne(long id) {
        ReservationTime found = reservationTimeRepository.findById(id).orElseThrow();

        return new ReservationTimeResponse(found);
    }

    public List<ReservationTimeResponse> findAll() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponse::new)
                .toList();
    }

    @Transactional
    public void remove(Long id) {
        if (reservationRepository.existsByTimeId(id)) {
            throw new BadRequestException("예약이 존재하는 시간은 삭제할 수 없습니다.");
        }
        reservationTimeRepository.deleteById(id);
    }

    public List<AvailableReservationTimeResponse> findAllWithAvailable(LocalDate date, Long themeId) {
        Set<Long> timeIds = new HashSet<>(reservationRepository.findTimeIdByDateAndThemeId(date, themeId));
        List<ReservationTime> times = reservationTimeRepository.findAll();

        return times.stream()
                .map(time -> new AvailableReservationTimeResponse(
                        time,
                        timeIds.contains(time.getId())))
                .toList();
    }
}

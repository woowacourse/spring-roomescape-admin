package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.ReservationEntity;
import roomescape.entity.ReservationTimeEntity;
import roomescape.exception.BadRequestException;
import roomescape.exception.ConflictException;
import roomescape.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao timeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    public List<ReservationResponseDto> getAllReservation() {
        return reservationDao.findAll()
                .stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    public ReservationResponseDto createReservation(ReservationRequestDto request) {
        ReservationTimeEntity timeEntity = timeDao.findById(request.timeId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 id 입니다."));

        ReservationEntity newReservation = request.toEntity(timeEntity);
        validateDateTime(newReservation);
        validateDuplicated(newReservation);

        ReservationEntity saved = reservationDao.save(newReservation);
        return ReservationResponseDto.from(saved);
    }

    private void validateDateTime(ReservationEntity reservation) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reservationDateTime = reservation.getDateTime();
        if (reservationDateTime.isBefore(now)) {
            throw new BadRequestException("과거 날짜/시간의 예약은 생성할 수 없습니다.");
        }
    }

    private void validateDuplicated(ReservationEntity newReservation) {
        List<ReservationEntity> reservations = reservationDao.findAll();
        if (reservations.stream().anyMatch(reservation -> reservation.isDuplicatedWith(newReservation))) {
            throw new ConflictException("해당 날짜에는 이미 예약이 존재합니다.");
        }
    }

    public void deleteReservation(final Long id) {
        final int deleted = reservationDao.deleteById(id);
        if (deleted == 0) {
            throw new NotFoundException("존재하지 않는 id 입니다.");
        }
    }
}

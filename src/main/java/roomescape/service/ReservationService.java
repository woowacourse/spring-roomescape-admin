package roomescape.service;

import java.util.List;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationResponseDto create(ReservationRequestDto requestDto) {
        try {
            ReservationTime reservationTime = reservationTimeDao.read(requestDto.timeId());
            Reservation reservation = reservationDao.create(requestDto, reservationTime);
            return ReservationResponseDto.from(reservation);
        } catch (IncorrectResultSizeDataAccessException exception) {
            // reservationTimeDao의 read 메서드에서 queryForObject 수행할 때 결과가 1개가 아니면 IncorrectResultSizeDateAccessException 발생
            throw new IllegalArgumentException("[ERROR] 해당 id의 예약 시간이 존재하지 않습니다.");
        }
    }

    public List<ReservationResponseDto> readAll() {
        List<Reservation> reservations = reservationDao.readAll();
        return reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    public void delete(Long id) {
        if (reservationDao.delete(id) == 0) {
            throw new IllegalArgumentException("[ERROR] 해당 id의 예약이 존재하지 않습니다.");
        }
    }
}

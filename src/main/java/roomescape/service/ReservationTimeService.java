package roomescape.service;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeRepository;
import roomescape.dto.ReservationTimeReqDto;
import roomescape.dto.ReservationTimeResDto;
import roomescape.model.ReservationTime;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeDAO;

    public ReservationTimeService(ReservationTimeRepository reservationTimeDAO) {
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public List<ReservationTimeResDto> findAll() {
        List<ReservationTime> reservationTimes = reservationTimeDAO.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeResDto::of)
                .toList();
    }

    public ReservationTimeResDto addAndGet(ReservationTimeReqDto dto) {
        ReservationTime newReservationTime = reservationTimeDAO.addAndGet(dto.startAt());
        return ReservationTimeResDto.of(newReservationTime);
    }

    public void deleteById(Long id) {
        int rows = reservationTimeDAO.deleteById(id);
        if (rows == 0) {
            throw new EmptyResultDataAccessException(rows);
        }
    }
}

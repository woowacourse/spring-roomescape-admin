package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.model.ReservationTime;
import roomescape.dao.JdbcReservationTimeDao;

@Service
public class ReservationTimeService {
    private final JdbcReservationTimeDao jdbcReservationTimeDao;

    public ReservationTimeService(JdbcReservationTimeDao jdbcReservationTimeDao) {
        this.jdbcReservationTimeDao = jdbcReservationTimeDao;
    }

    public ReservationTimeResponse addTime(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime time = reservationTimeRequest.toEntity();
        ReservationTime savedTime = jdbcReservationTimeDao.save(time);
        return ReservationTimeResponse.fromEntity(savedTime);
    }

    public void deleteTime(Long id) {
        boolean isDeleted = jdbcReservationTimeDao.deleteById(id);
        if (!isDeleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 id가 없습니다");
        }
    }

    public List<ReservationTimeResponse> getReservationTimes() {
        return jdbcReservationTimeDao.findAll().stream()
                .map(ReservationTimeResponse::fromEntity)
                .toList();
    }
}

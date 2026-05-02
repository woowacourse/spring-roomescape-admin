package roomescape.service;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import roomescape.dao.QueryingDAO;
import roomescape.dao.UpdatingDAO;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationCreateResponse;
import roomescape.dto.TimeCreateResponse;

@Service
public class ReservationService {
    private final JdbcTemplate jdbcTemplate;

    public ReservationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationCreateResponse createReservation(ReservationCreateRequest request) {
        UpdatingDAO dao = new UpdatingDAO(jdbcTemplate);
        QueryingDAO daoQuery = new QueryingDAO(jdbcTemplate);
        ReservationTime reservationTime = daoQuery.findTimeById(request.timeId());
        TimeCreateResponse timeCreateResponse = new TimeCreateResponse(request.timeId(), reservationTime.getStartAt());

        Reservation reservation = new Reservation(request.name(), request.date(), reservationTime);
        Long id = dao.insertWithKeyHolder(reservation, request.timeId());
        return new ReservationCreateResponse(id, reservation.getName(), reservation.getDate(), timeCreateResponse);
    }

    public List<ReservationCreateResponse> readAllReservations() {
        QueryingDAO dao = new QueryingDAO(jdbcTemplate);
        return dao.findAllReservations();
    }

    public void deleteReservation(Long id) {
        UpdatingDAO dao = new UpdatingDAO(jdbcTemplate);
        dao.delete(id);
    }

    public TimeCreateResponse createTime(String startAt) {
        UpdatingDAO dao = new UpdatingDAO(jdbcTemplate);
        ReservationTime reservationTime = new ReservationTime(startAt);
        Long id = dao.insertWithKeyHolder(reservationTime);
        return new TimeCreateResponse(id, startAt);
    }

    public List<TimeCreateResponse> readAllTimes() {
        QueryingDAO dao = new QueryingDAO(jdbcTemplate);
        return dao.findAllTimes();
    }

    public void deleteTime(Long id) {
        UpdatingDAO dao = new UpdatingDAO(jdbcTemplate);
        dao.deleteTime(id);
    }
}

package roomescape.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.domain.ReservationTime;
import roomescape.util.CustomDateTimeFormatter;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTime> findReservationTimes() {
        return jdbcTemplate.query(
                "SELECT * FROM reservation_time"
                , (rs, rowNum) -> new ReservationTime(
                        rs.getLong("id"),
                        CustomDateTimeFormatter.getLocalTime(rs.getString("start_at"))
                )
        );
    }

    public Long createReservationTime(ReservationTimeCreateRequest reservationTimeCreateRequest) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
        return simpleJdbcInsert.executeAndReturnKey(Map.of(
                "start_at", reservationTimeCreateRequest.startAt()
        )).longValue();
    }

    public Optional<ReservationTime> findReservationTimeById(Long createdReservationTimeId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM reservation_time WHERE id = ?",
                (rs, rowNum) -> new ReservationTime(
                        rs.getLong("id"),
                        CustomDateTimeFormatter.getLocalTime(rs.getString("start_at")
                        )),
                createdReservationTimeId));
    }

    public void deleteReservationTimeById(Long id) {
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
    }
}

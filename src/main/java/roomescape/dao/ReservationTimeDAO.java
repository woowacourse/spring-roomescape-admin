package roomescape.dao;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.dto.response.ReservationTimeCreateResponse;
import roomescape.dto.response.ReservationTimeFindAllResponse;

@Repository
public class ReservationTimeDAO {

    private JdbcTemplate jdbcTemplate;

    public ReservationTimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTimeCreateResponse insert(ReservationTime reservationTime) {
        jdbcTemplate.update("insert into reservation_time (start_at) values (?)", reservationTime.getStartAt());

        Long id = jdbcTemplate.queryForObject("select t.id from reservation_time t where t.start_at = ?", Long.class,
                reservationTime.getStartAt());

        return ReservationTimeCreateResponse.of(id, reservationTime.getStartAt());
    }

    public List<ReservationTimeFindAllResponse> findAll() {
        String sql = "select id, start_at from reservation_time";
        RowMapper<ReservationTime> rowMapper = (resultSet, rowNum) -> ReservationTime.of(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
        );

        List<ReservationTime> reservationTimes = jdbcTemplate.query(sql, rowMapper);
        List<ReservationTimeFindAllResponse> response = reservationTimes.stream()
                .map(it -> ReservationTimeFindAllResponse.of(it.getId(), it.getStartAt()))
                .toList();
        return response;
    }

    public ReservationTime findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "select id, start_at from reservation_time where id = ?",
                    (resultSet, rowNum) -> ReservationTime.of(resultSet.getLong("id"), resultSet.getString("start_at")),
                    id
            );
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 예약 시간입니다.");
        }
    }

    public void delete(Long id) {
        jdbcTemplate.update("delete from reservation_time where id = ?", id);
    }
}

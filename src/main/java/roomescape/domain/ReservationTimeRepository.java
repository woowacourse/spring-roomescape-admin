package roomescape.domain;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public Optional<ReservationTime> findById(Long id) {
        String sql = "select id, start_at from reservation_time where id = ?";
        ReservationTime findReservationTime = jdbcTemplate.queryForObject(sql,
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getLong("id"),
                        resultSet.getTime("start_at").toLocalTime()
                ), id);
        return Optional.ofNullable(findReservationTime);
    }

    public ReservationTime create(ReservationTime reservationTime) {
        LocalTime startAt = reservationTime.getStartAt();
        try {
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("start_at", reservationTime.getStartAt());
            long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
            return new ReservationTime(id, startAt);
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException(String.format("이미 존재하는 예약 시간입니다. 입력 시간:%s", startAt));
        }
    }

    public List<ReservationTime> findAll() {
        String sql = "select id, start_at from reservation_time";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        ));
    }

    public void deleteById(Long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }
}

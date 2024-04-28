package roomescape.domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationTimeRepositoryImpl implements ReservationTimeRepository {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");

    }

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("SELECT id, start_at FROM reservation_time", reservationTimeRowMapper());
    }

    public Optional<ReservationTime> findById(Long id) {
        try {
            ReservationTime reservationTime = jdbcTemplate.queryForObject(
                    "SELECT id, start_at FROM reservation_time WHERE id = ?",
                    reservationTimeRowMapper(), id);
            return Optional.ofNullable(reservationTime);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public ReservationTime create(ReservationTime reservationTime) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("start_at", reservationTime.getStartAt().format(TIME_FORMATTER));
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return findById(id).orElseThrow(() -> new NoSuchElementException("예약 시간이 생성되지 않았습니다."));
    }

    private RowMapper<ReservationTime> reservationTimeRowMapper() {
        return (resultSet, rowNum) -> {
            LocalTime startAt = LocalTime.parse(resultSet.getString("start_at"));
            ReservationTime reservationTime = new ReservationTime(resultSet.getLong("id"), startAt);
            return reservationTime;
        };
    }

    public void removeById(Long id) {
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
    }
}

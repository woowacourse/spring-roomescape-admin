package roomescape.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeDao {

    private static final RowMapper<ReservationTime> RESERVATION_TIME_ROW_MAPPER = getReservationTimeRowMapper();

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    private static RowMapper<ReservationTime> getReservationTimeRowMapper() {
        return (resultSet, rowNum) -> new ReservationTime(resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("start_at")));
    }

    public ReservationTime save(ReservationTime reservationTime) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(reservationTime);
        Number key = jdbcInsert.executeAndReturnKey(parameterSource);

        return new ReservationTime(key.longValue(), reservationTime.getStartAt());
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(sql, RESERVATION_TIME_ROW_MAPPER);
    }

    public Optional<ReservationTime> findById(Long id) {
        String sql = """
                SELECT * FROM reservation_time
                WHERE id = ?""";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, RESERVATION_TIME_ROW_MAPPER, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void delete(ReservationTime reservationTime) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, reservationTime.getId());
    }

    public void deleteAll() {
        String sql = "DELETE FROM reservation_time";
        jdbcTemplate.update(sql);
    }
}

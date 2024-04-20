package roomescape.repository;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationTimeRepository(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final ReservationTime reservationTime) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(reservationTime);
        return jdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("SELECT id, start_at FROM reservation_time", getReservationTimeRowMapper());
    }

    public ReservationTime findById(final long id) {
        try {
            final String query = "SELECT id, start_at FROM reservation_time WHERE id = ?";
            return jdbcTemplate.queryForObject(query, getReservationTimeRowMapper(), id);
        } catch (DataAccessException e) {
            throw new IllegalArgumentException("Reservation time not found");
        }
    }

    private RowMapper<ReservationTime> getReservationTimeRowMapper() {
        return (resultSet, rowNum) -> {
            final Long timeId = resultSet.getLong("id");
            final String timeValue = resultSet.getString("start_at");

            return new ReservationTime(timeId, timeValue);
        };
    }

    public void deleteById(final long id) {
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
    }
}

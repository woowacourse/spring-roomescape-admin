package roomescape.reservation.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.ReservationTime;

import javax.sql.DataSource;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationTimeDao {
    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;

    public ReservationTimeDao(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public ReservationTime insert(final ReservationTime reservationTime) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(reservationTime);
        long id = jdbcInsert.executeAndReturnKey(param).longValue();

        return new ReservationTime(id, reservationTime);
    }

    public ReservationTime findById(final Long id) {
        String sql = "SELECT * FROM reservation_time WHERE id = (?)";

        return jdbcTemplate.queryForObject(sql, ROW_MAPPER, id);
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM reservation_time";

        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public int deleteById(final Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = (?)";
        int deleteCount = jdbcTemplate.update(sql, id);

        return deleteCount;
    }

    private static final RowMapper<ReservationTime> ROW_MAPPER = (rs, rowNum) -> {
        ReservationTime reservationTime = new ReservationTime(
                rs.getLong("id"),
                rs.getObject("start_at", LocalTime.class)
        );

        return reservationTime;
    };
}

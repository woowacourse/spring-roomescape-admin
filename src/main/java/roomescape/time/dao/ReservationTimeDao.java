package roomescape.time.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.time.domain.ReservationTime;

import javax.sql.DataSource;
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

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("SELECT * FROM reservation_time", ROW_MAPPER);
    }

    public int deleteById(final Long id) {
        int deleteCount = jdbcTemplate.update("DELETE FROM reservation_time WHERE id = (?)", id);

        return deleteCount;
    }

    private static final RowMapper<ReservationTime> ROW_MAPPER = (rs, rowNum) -> {
        ReservationTime reservationTime = new ReservationTime(
                rs.getLong("id"),
                rs.getString("start_at")
        );

        return reservationTime;
    };
}

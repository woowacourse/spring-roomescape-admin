package roomescape.time.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.time.domain.ReservationTime;

import javax.sql.DataSource;

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
}

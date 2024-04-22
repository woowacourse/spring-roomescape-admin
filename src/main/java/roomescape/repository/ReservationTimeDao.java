package roomescape.repository;

import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;

@Repository
public class ReservationTimeDao {

    private final SimpleJdbcInsert jdbcInsert;

    public ReservationTimeDao(DataSource source) {
        this.jdbcInsert = new SimpleJdbcInsert(source)
            .withTableName("reservation_time")
            .usingGeneratedKeyColumns("id");
    }

    public ReservationTime save(ReservationTime reservationTime) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(reservationTime);
        Long id = jdbcInsert.executeAndReturnKey(params)
            .longValue();
        return new ReservationTime(id, reservationTime.getStartAt());
    }
}

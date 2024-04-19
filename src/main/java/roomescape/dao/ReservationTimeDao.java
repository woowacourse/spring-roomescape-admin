package roomescape.dao;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationTimeRequest;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert reservationInsert;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.reservationInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public Long insert(ReservationTimeRequest reservationTimeRequest) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(reservationTimeRequest);
        Number id = reservationInsert.executeAndReturnKey(parameterSource);
        return id.longValue();
    }
}

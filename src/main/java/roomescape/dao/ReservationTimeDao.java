package roomescape.dao;

import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeCreateRequest;

@Repository
public class ReservationTimeDao {

    private static final String DATABASE = "reservation_time";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM " + DATABASE;

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    ReservationTime reservationTime
                            = new ReservationTime(
                            resultSet.getLong("id"),
                            LocalTime.parse(resultSet.getString("start_at"))
                    );
                    return reservationTime;
                }
        );
    }

    public Long addReservationTime(ReservationTimeCreateRequest reservationTimeCreateRequest) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("start_at", reservationTimeCreateRequest.startAt());
        return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    public int deleteById(Long id) {
        String sql = "delete from " + DATABASE + " where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

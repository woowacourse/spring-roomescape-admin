package roomescape.repository;

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
public class ReservationTimeRepository {

    private static final String TABLE_NAME = "reservation_time";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM " + TABLE_NAME;

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

    public ReservationTime save(ReservationTimeCreateRequest reservationTimeCreateRequest) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("start_at", reservationTimeCreateRequest.startAt());
        Long id = simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
        return new ReservationTime(id, reservationTimeCreateRequest.startAt());
    }

    public int deleteById(Long id) {
        String sql = "delete from " + TABLE_NAME + " where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

package roomescape.persistence.repository.reservationtime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.ReservationTime;

@Repository
public class ReservationTimeJdbcRepository implements ReservationTimeRepository {

    private static final String RESERVATION_TIME_TABLE = "reservation_time";
    private static final String RESERVATION_TIME_ID = "id";
    private static final String RESERVATION_TIME_START_AT = "start_at";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationTimeJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName(RESERVATION_TIME_TABLE)
                .usingColumns(RESERVATION_TIME_START_AT)
                .usingGeneratedKeyColumns(RESERVATION_TIME_ID);
    }

    @Override
    public Long addAndGetId(ReservationTime reservationTime) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(RESERVATION_TIME_START_AT, reservationTime.getStartAt());

        return jdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public ReservationTime findById(Long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,
                (resultSet, rowNum) -> getReservationTimeData(resultSet), id);
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> getReservationTimeData(resultSet));
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private ReservationTime getReservationTimeData(ResultSet resultSet) throws SQLException {
        return new ReservationTime(
                resultSet.getLong(RESERVATION_TIME_ID),
                resultSet.getObject(RESERVATION_TIME_START_AT, LocalTime.class)
        );
    }
}

package roomescape.persistence.repository.reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

@Repository
public class ReservationJdbcRepository implements ReservationRepository {

    private static final String RESERVATION_TABLE = "reservation";
    private static final String RESERVATION_ID = "id";
    private static final String RESERVATION_NAME = "name";
    private static final String RESERVATION_DATE = "date";

    private static final String RESERVATION_TIME_ID = "time_id";
    private static final String RESERVATION_TIME_START_AT = "start_at";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName(RESERVATION_TABLE)
                .usingColumns(RESERVATION_NAME, RESERVATION_DATE, RESERVATION_TIME_ID)
                .usingGeneratedKeyColumns(RESERVATION_ID);
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT r.id AS id, r.name, r.date, rt.id AS time_id, rt.start_at "
                + "FROM reservation r "
                + "INNER JOIN reservation_time rt "
                + "ON r.time_id = rt.id ";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> getReservationData(resultSet));
    }

    @Override
    public Long addAndGetId(Reservation reservation) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(RESERVATION_NAME, reservation.getName())
                .addValue(RESERVATION_DATE, reservation.getDate())
                .addValue(RESERVATION_TIME_ID, reservation.getTime().getId());

        return jdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Reservation findById(Long id) {
        String selectSql = "SELECT r.id AS id, r.name, r.date, rt.id AS time_id, rt.start_at "
                + "FROM reservation r "
                + "INNER JOIN reservation_time rt "
                + "ON r.time_id = rt.id "
                + "WHERE r.id = ?";

        return jdbcTemplate.queryForObject(selectSql,
                (resultSet, rowNum) -> getReservationData(resultSet),
                id);
    }

    private Reservation getReservationData(ResultSet resultSet) throws SQLException {
        ReservationTime reservationTime = new ReservationTime(
                resultSet.getLong(RESERVATION_TIME_ID),
                resultSet.getObject(RESERVATION_TIME_START_AT, LocalTime.class)
        );

        return new Reservation(
                resultSet.getLong(RESERVATION_ID),
                resultSet.getString(RESERVATION_NAME),
                resultSet.getObject(RESERVATION_DATE, LocalDate.class),
                reservationTime
        );
    }
}

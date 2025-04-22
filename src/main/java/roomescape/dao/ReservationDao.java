package roomescape.dao;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertReservation;
    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) ->
            new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getTime("time").toLocalTime()
            );

    public ReservationDao(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertReservation = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> getReservations() {
        final String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Reservation createReservation(final String name, final LocalDate date, final LocalTime time) {
        Long id = insertReservationAndRetrieveKey(name, date, time);
        return getReservationById(id);
    }

    public void deleteReservationById(final Long id) {
        final String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private Reservation getReservationById(final Long id) {
        final String sql = "SELECT id, name, date, time FROM reservation where id = ?";
        return jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
    }

    private Long insertReservationAndRetrieveKey(final String name, final LocalDate date, final LocalTime time) {
        final Map<String, Object> parameters = new HashMap<>(Map.of(
                "name", name,
                "date", Date.valueOf(date),
                "time", Time.valueOf(time))
        );
        return (Long) insertReservation.executeAndReturnKey(parameters);
    }
}

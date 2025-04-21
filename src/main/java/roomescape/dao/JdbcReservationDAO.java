package roomescape.dao;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class JdbcReservationDAO implements ReservationDAO {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Reservation> reservationRowMapper;

    public JdbcReservationDAO(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
        this.reservationRowMapper = (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime());
    }

    @Override
    public List<Reservation> findAll() {
        String query = "SELECT * FROM reservation";
        return jdbcTemplate.query(query, reservationRowMapper);
    }

    @Override
    public boolean existsByDateAndTime(final LocalDate date, final LocalTime time) {
        String query = "SELECT COUNT(*) FROM reservation WHERE date = ? AND time = ?";
        Integer found = jdbcTemplate.queryForObject(query, Integer.class, date, time);
        return found != null && found > 0;
    }

    @Override
    public long insert(final Reservation reservation) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", Date.valueOf(reservation.getDate()))
                .addValue("time", Time.valueOf(reservation.getTime()));
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        return newId.longValue();
    }

    @Override
    public boolean deleteById(final long id) {
        String query = "DELETE FROM reservation WHERE id = ?";
        int deleted = jdbcTemplate.update(query, id);
        return deleted > 0;
    }
}

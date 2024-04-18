package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservations")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findReservations() {
        String sql = "SELECT id, name, date, time FROM reservations";
        return jdbcTemplate.query(sql, rowMapper());
    }

    public Reservation createReservation(Reservation reservation) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(reservation);
        long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public void removeReservation(Long id) {
        jdbcTemplate.update("DELETE reservations WHERE id = ?", id);
    }

    private RowMapper<Reservation> rowMapper() {
        return (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime());
    }
}

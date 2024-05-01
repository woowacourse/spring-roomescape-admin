package roomescape.dao.web;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dao.ReservationRepository;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReservationJdbcRepository implements ReservationRepository {

    private static final RowMapper<Reservation> rowMapper;

    static {
        rowMapper = (rs, rowNum) -> new Reservation(
                rs.getLong("r_id"),
                rs.getString("r_name"),
                rs.getDate("r_date").toLocalDate(),
                new ReservationTime(
                        rs.getLong("t_id"),
                        rs.getTime("t_start_at").toLocalTime())
        );
    }

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationJdbcRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservations")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time_id", reservation.getReservationTime().getId());
        long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getReservationTime());
    }

    @Override
    public List<Reservation> findReservations() {
        String sql = "SELECT r.id as r_id, r.name as r_name, r.date as r_date, t.id as t_id, t.start_at as t_start_at FROM reservations as r INNER JOIN reservation_times as t ON r.time_id = t.id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void removeReservation(Long id) {
        jdbcTemplate.update("DELETE reservations WHERE id = ?", id);
    }
}

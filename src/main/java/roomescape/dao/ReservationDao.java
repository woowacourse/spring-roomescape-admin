package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.Reservation;
import roomescape.ReservationTime;
import roomescape.dto.ReservationRequest;

@Repository
public class ReservationDao {

    private JdbcTemplate jdbcTemplate;

    public ReservationDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Reservation> findAll() {
        String sql = "SELECT "
                + "r.id as reservation_id, "
                + "r.name, "
                + "r.date, "
                + "t.id as time_id, "
                + "t.start_at as time_value "
                + "FROM reservation as r "
                + "inner join reservation_time as t "
                + "on r.time_id = t.id";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new Reservation(rs.getLong("reservation_id"), rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        new ReservationTime(rs.getLong("time_id"), rs.getTime("time_value").toLocalTime())));
    }

    public Reservation findById(long id) {
        String sql = "SELECT "
                + "r.id as reservation_id, "
                + "r.name, "
                + "r.date, "
                + "t.id as time_id, "
                + "t.start_at as time_value "
                + "FROM reservation as r "
                + "inner join reservation_time as t "
                + "on r.time_id = t.id "
                + "WHERE t.id = ?";
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new Reservation(rs.getLong("id"), rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        new ReservationTime(rs.getLong("time_id"), rs.getTime("time_value").toLocalTime())),
                id);
    }

    public long save(ReservationRequest reservationRequest) {
        String sql = "INSERT INTO reservation (name, date, time_id) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql, new String[]{"id"});
            ps.setString(1, reservationRequest.name());
            ps.setString(2, String.valueOf(reservationRequest.date()));
            ps.setString(3, String.valueOf(reservationRequest.timeId()));
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

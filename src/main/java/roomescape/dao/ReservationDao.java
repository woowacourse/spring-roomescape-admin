package roomescape.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.Reservation;
import roomescape.ReservationTime;
import roomescape.dto.ReservationDto;
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
                + "on r.time_id = t.id\n";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new Reservation(rs.getLong("reservation_id"), rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        new ReservationTime(rs.getLong("time_id"), rs.getTime("time_value").toLocalTime())));
    }

    public ReservationDto findById(long id) {
        String sql = "SELECT id, name, date, time_id FROM reservation WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new ReservationDto(rs.getLong("id"), rs.getString("name"),
                        rs.getDate("date").toLocalDate(), rs.getLong("time_id")),
                id);
    }

    public int save(ReservationRequest reservationRequest) {
        String sql = "INSERT INTO reservation (name, date, time_id) values (?, ?, ?)";
        return jdbcTemplate.update(sql, reservationRequest.name(), reservationRequest.date(), reservationRequest.timeId());
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

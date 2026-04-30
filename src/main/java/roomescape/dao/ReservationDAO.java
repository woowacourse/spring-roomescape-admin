package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationDAO {

    private JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation insert(String name, String date, Long timeId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        long id = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, name);
            ps.setString(2, date);
            ps.setLong(3, timeId);
            return ps;
        }, keyHolder);

        ReservationTime time = jdbcTemplate.queryForObject(
                "select id, start_at from reservation_time where id = ?",
                (resultSet, rowNum) -> ReservationTime.of(resultSet.getLong("id"), resultSet.getString("start_at")),
                timeId
        );

        return Reservation.of(id, name, date, time);
    }

    public List<Reservation> findAll() {
        String sql = "select r.id, r.name, r.date, t.id as time_id, t.start_at "
                + "from reservation r inner join reservation_time t on r.time_id = t.id";

        RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> Reservation.of(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                ReservationTime.of(resultSet.getLong("time_id"), resultSet.getString("start_at"))
        );

        return jdbcTemplate.query(sql, rowMapper);
    }

    public void delete(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}

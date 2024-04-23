package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationAddRequest;

@Repository
public class ReservationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                new ReservationTime(resultSet.getLong("time_id"), resultSet.getTime("time_value").toLocalTime())
        );

    public List<Reservation> findAll() {
        String sql = "SELECT"
                + "    r.id as reservation_id,"
                + "    r.name,"
                + "    r.date,"
                + "    t.id as time_id,"
                + "    t.start_at as time_value "
                + "FROM reservation as r "
                + "INNER JOIN reservation_time as t "
                + "ON r.time_id = t.id ";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Reservation findById(Long id) {
        String sql = "SELECT"
                + "    r.id as reservation_id,"
                + "    r.name,"
                + "    r.date,"
                + "    t.id as time_id,"
                + "    t.start_at as time_value "
                + "FROM reservation as r "
                + "INNER JOIN reservation_time as t "
                + "ON r.time_id = t.id "
                + "WHERE r.id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public Reservation insert(ReservationAddRequest reservationAddRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation (name, date, time_id) values (?,?,?)",
                    new String[]{"id"}
            );
            ps.setString(1, reservationAddRequest.getName());
            ps.setString(2, reservationAddRequest.getDate().toString());
            ps.setLong(3, reservationAddRequest.getTimeId());
            return ps;
        }, keyHolder);
        Long id = keyHolder.getKey().longValue();
        return findById(id);
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}

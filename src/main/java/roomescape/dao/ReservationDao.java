package roomescape.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("reservation_id"),
                Name.from(resultSet.getString("name")),
                LocalDate.parse(resultSet.getString("date")),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        LocalTime.parse(resultSet.getString("time_value"))
                )
        );
        return reservation;
    };

    public Reservation save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservation(name, `date`, `time_id`) VALUES (?,?,?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName().getValue());
            ps.setDate(2, Date.valueOf(reservation.getDate()));
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();

        return new Reservation(id, reservation);
    }

    public List<Reservation> findAll() {
        String sql = "SELECT \n"
                + "    r.id as reservation_id, \n"
                + "    r.name, \n"
                + "    r.date, \n"
                + "    t.id as time_id, \n"
                + "    t.start_at as time_value \n"
                + "FROM reservation as r \n"
                + "inner join reservation_time as t \n"
                + "on r.time_id = t.id";

        return jdbcTemplate.query(
                sql, reservationRowMapper
        );
    }

    public Reservation findById(long id) {
        String sql = "SELECT \n"
                + "    r.id as reservation_id, \n"
                + "    r.name, \n"
                + "    r.date, \n"
                + "    t.id as time_id, \n"
                + "    t.start_at as time_value \n"
                + "FROM reservation as r \n"
                + "inner join reservation_time as t \n"
                + "on r.time_id = t.id  \n"
                + "WHERE r.id = ?";

        return jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}

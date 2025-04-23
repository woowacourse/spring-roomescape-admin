package roomescape.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;
import roomescape.model.ReservationDateTime;

@Repository
public class ReservationDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                new ReservationDateTime(
                        LocalDateTime.of(
                                resultSet.getDate("date").toLocalDate(),
                                resultSet.getTime("startAt").toLocalTime())
                ));
        return reservation;
    };


    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    public Long saveReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, startAt) values (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setDate(2, Date.valueOf(reservation.getDate()));
            ps.setTime(3, Time.valueOf(reservation.getTime()));
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Reservation findReservationById(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        Reservation reservation = jdbcTemplate.queryForObject(sql, actorRowMapper, id);
        return reservation;
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

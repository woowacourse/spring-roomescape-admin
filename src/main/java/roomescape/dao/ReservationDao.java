package roomescape.dao;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
                                resultSet.getTime("time").toLocalTime())
                ));
        return reservation;
    };


    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    public void saveReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) value(?,?,?)";
        jdbcTemplate.update(sql, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public Reservation findByNameAndDateTime(Reservation reservation) {
        String sql = "SELECT * FROM reservation WHERE (name, date, time) value(?,?,?)";
        Reservation findReservation = jdbcTemplate.queryForObject(
                sql, actorRowMapper,reservation.getName(), reservation.getDate(), reservation.getTime());
        return findReservation;
    }
}

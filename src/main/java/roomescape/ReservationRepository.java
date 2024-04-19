package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.RequestReservation;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long add(RequestReservation requestReservation) {
        String SQL = "INSERT INTO RESERVATION (name, date, time) VALUES(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL, new String[]{"id"});
            ps.setString(1, requestReservation.name());
            ps.setString(2, requestReservation.date());
            ps.setString(3, requestReservation.time());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public List<Reservation> findAll() {
        String SQL = "SELECT * FROM RESERVATION";
        return jdbcTemplate.query(SQL, (resultSet, rowNum) -> {
            Reservation reservation = new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    resultSet.getString("time")
            );
            return reservation;
        });
    }

    public Reservation remove(Long id) {
        return null;
    }
}

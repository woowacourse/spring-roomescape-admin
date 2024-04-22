package roomescape.db;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;


@Repository
public class ReservationRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Long createReservation(ReservationRequest reservationRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation (name,date,time) values (?,?,?)",
                    new String[]{"id"});
            ps.setString(1, reservationRequest.name());
            ps.setObject(2, reservationRequest.date());
            ps.setObject(3, reservationRequest.time());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Reservation findById(final Long id) {
        return jdbcTemplate.queryForObject("select id, name, time, date from reservation where id=?",
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        LocalDateTime.of(
                                LocalDate.parse(resultSet.getString("date")),
                                LocalTime.parse(resultSet.getString("time"))
                        )
                ), id);

    }

    public List<Reservation> getReservations() {
        return jdbcTemplate.query("select id, name, time, date from reservation",
                (resultSet, rowNum) -> new Reservation(resultSet.getLong("id"), resultSet.getString("name"),
                        LocalDateTime.of(LocalDate.parse(resultSet.getString("date")),
                                LocalTime.parse(resultSet.getString("time")))));
    }

    public void deleteById(final long id) {
        jdbcTemplate.update("delete from reservation where id=?", id);
    }
}

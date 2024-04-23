package roomescape.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

@Repository
public class JdbcTemplateReservationRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(ReservationRequest reservationRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Reservation reservation = fromRequest(reservationRequest);
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(
                    "insert into reservation (name, date,time) values ( ?,?,? )", new String[]{"id"});
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setDate(2, Date.valueOf(reservation.getDate()));
            preparedStatement.setTime(3, Time.valueOf(reservation.getTime()));
            return preparedStatement;
        }, keyHolder);
        return new Reservation(keyHolder.getKey().longValue(), reservation);
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query("select * from reservation", (rs, rowNum) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            LocalDate date = rs.getDate("date").toLocalDate();
            LocalTime time = rs.getTime("time").toLocalTime();
            return new Reservation(id, name, date, time);
        });
    }

    @Override
    public void delete(long reservationId) {
        jdbcTemplate.update("delete from reservation where id = ?", reservationId);
    }

    private Reservation fromRequest(ReservationRequest reservationRequest) {
        long id = 1L;
        String name = reservationRequest.name();
        LocalDate date = reservationRequest.date();
        LocalTime time = reservationRequest.time();
        return new Reservation(id, name, date, time);
    }
}

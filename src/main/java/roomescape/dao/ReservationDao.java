package roomescape.dao;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationDateTime;
import roomescape.domain.ReservationName;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> getReservations() {
        String sql = """
                SELECT 
                    r.id as reservation_id, 
                    r.name, 
                    r.date, 
                    t.id as time_id, 
                    t.start_at as time_value 
                FROM reservation as r 
                inner join reservation_time as t 
                on r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                new ReservationName(resultSet.getString("name")),
                new ReservationDate(parseDate(resultSet.getString("date"))),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        parseTime(resultSet.getString("time_value"))
                )
        ));
    }

    public Reservation createReservation(final ReservationName name, final ReservationDateTime dateTime) {
        String sql = "insert into reservation(name, date, time_id) values(?, ?, ?)";
        String[] resultColumns = {"id"};
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, resultColumns);
            statement.setString(1, name.getValue());
            statement.setString(2, dateTime.date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            statement.setLong(3, dateTime.reservationTime().id());
            return statement;
        }, keyHolder);
        return new Reservation(
                Objects.requireNonNull(keyHolder.getKey()).longValue(),
                name,
                dateTime.reservationDate(),
                dateTime.reservationTime()
        );
    }

    public void deleteReservationById(final Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }

    private LocalDate parseDate(final String date) {
        return LocalDate.parse(date);
    }

    private LocalTime parseTime(final String time) {
        return LocalTime.parse(time);
    }
}

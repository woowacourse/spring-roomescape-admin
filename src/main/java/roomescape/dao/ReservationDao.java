package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insert(final Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDateTime().toLocalDate().toString());
            ps.setString(3, reservation.getDateTime().toLocalTime().toString());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Reservation> findAllReservations() {
        String query = "SELECT * FROM reservation";
        return jdbcTemplate.query(query, reservationRowMapper());
    }

    public int delete(final Long id) {
        String query = "DELETE FROM reservation WHERE id = ?";
        int count = jdbcTemplate.update(query, id);
        return count;
    }

    public void deleteAll() {
        String query = "DELETE FROM reservation";
        jdbcTemplate.update(query);
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                toLocalDateTime(
                        resultSet.getString("date"),
                        resultSet.getString("time")
                ));
    }

    private LocalDateTime toLocalDateTime(String date, String time) {
        LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalTime parsedTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
        return LocalDateTime.of(parsedDate, parsedTime);
    }
}

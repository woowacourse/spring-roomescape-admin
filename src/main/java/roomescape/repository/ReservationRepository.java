package roomescape.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> readAll() {
        String sql = "select * from reservation";
        return jdbcTemplate.query(sql, reservationRowMapper());
    }

    public Reservation save(Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setDate(2, Date.valueOf(reservation.getStartDate()));
            ps.setTime(3, Time.valueOf(reservation.getStartTime()));
            return ps;
        }, keyHolder);
        long savedId = keyHolder.getKey().longValue();
        return new Reservation(savedId, reservation.getName(), reservation.getStartDate(), reservation.getStartTime());
    }

    public Optional<Reservation> findById(long id) {
        String sql = "select * from reservation where id=?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, new Object[]{id}, reservationRowMapper()));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    public void deleteById(long id) {
        String sql = "delete from reservation where id=?";
        jdbcTemplate.update(sql, id);
    }

    public boolean isAnyReservationConflictWith(Reservation reservation) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startDateTime = reservation.getStartDateTime().format(formatter);
        String endDateTime = reservation.getEndDateTime().format(formatter);

        String sql = "select exists (" +
                "    select 1 " +
                "    from reservation " +
                "    where ? between (date || ' ' || time) and dateadd('HOUR', ?, (date || ' ' || time)) " +
                "    or ? between (date || ' ' || time) and dateadd('HOUR', ?, (date || ' ' || time)) " +
                ") as exists_overlap;";

        boolean conflict = jdbcTemplate.queryForObject(sql, Boolean.class, endDateTime,
                ReservationTime.RESERVATION_DURATION_HOUR, startDateTime, ReservationTime.RESERVATION_DURATION_HOUR);
        return conflict;
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (rs, rowNum) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            LocalDate date = rs.getDate("date").toLocalDate();
            LocalTime time = rs.getTime("time").toLocalTime();

            return new Reservation(id, name, date, time);
        };
    }
}

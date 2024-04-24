package roomescape.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
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
import roomescape.entity.GameTime;
import roomescape.entity.Reservation;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> readAll() {
        String sql = "select r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value "
                + "from reservation as r "
                + "inner join reservation_time as t on t.id = r.time_id";
        return jdbcTemplate.query(sql, reservationRowMapper());
    }

    public Reservation save(Reservation reservation) {
        String sql = "insert into reservation (name, date, time_id) values(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setDate(2, Date.valueOf(reservation.getStartDate()));
            ps.setLong(3, reservation.getGameTimeId());
            return ps;
        }, keyHolder);
        long savedId = keyHolder.getKey().longValue();
        GameTime time = new GameTime(reservation.getGameTimeId(), reservation.getStartTime());
        return new Reservation(savedId, reservation.getName(), reservation.getStartDate(), time);
    }

    public Optional<Reservation> findById(long id) {
        String sql = "select r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value "
                + "from reservation as r "
                + "inner join reservation_time as t on t.id = r.time_id "
                + "where r.id=?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, reservationRowMapper(), id));
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
                "    from reservation as r " +
                "    inner join reservation_time as t" +
                "    where ? between (r.date||' '||t.start_at) and dateadd('HOUR', ?, (r.date||' '||t.start_at)) " +
                "    or ? between (r.date||' '||t.start_at) and dateadd('HOUR', ?, (r.date||' ' ||t.start_at)) " +
                ") as exists_overlap;";

        boolean conflict = jdbcTemplate.queryForObject(sql, Boolean.class, endDateTime, Reservation.TIME_DURATION,
                startDateTime, Reservation.TIME_DURATION);
        return conflict;
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (rs, rowNum) -> {
            long id = rs.getLong("reservation_id");
            String name = rs.getString("name");
            LocalDate date = rs.getDate("date").toLocalDate();

            long timeId = rs.getLong("time_id");
            LocalTime time = rs.getTime("start_at").toLocalTime();

            return new Reservation(id, name, date, new GameTime(timeId, time));
        };
    }
}

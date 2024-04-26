package roomescape.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entity.GameTime;
import roomescape.entity.Reservation;

@Repository
public class ReservationRepository {
    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (rs, rowNum) -> {
        long id = rs.getLong("reservation_id");
        String name = rs.getString("name");
        LocalDate date = rs.getDate("date").toLocalDate();

        long timeId = rs.getLong("time_id");
        LocalTime time = rs.getTime("start_at").toLocalTime();

        return new Reservation(id, name, date, new GameTime(timeId, time));
    };

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> readAll() {
        String sql = "select r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value "
                + "from reservation as r "
                + "inner join reservation_time as t on t.id = r.time_id";
        return jdbcTemplate.query(sql, RESERVATION_ROW_MAPPER);
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

    public Reservation findById(long id) {
        String sql = "select r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value "
                + "from reservation as r "
                + "inner join reservation_time as t on t.id = r.time_id "
                + "where r.id=?";
        return jdbcTemplate.queryForObject(sql, RESERVATION_ROW_MAPPER, id);
    }

    public void deleteById(long id) {
        String sql = "delete from reservation where id=?";
        jdbcTemplate.update(sql, id);
    }
}

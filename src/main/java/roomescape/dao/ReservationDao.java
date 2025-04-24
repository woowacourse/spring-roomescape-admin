package roomescape.dao;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.domain_entity.Id;
import roomescape.domain_entity.Reservation;
import roomescape.dto.ReservationRequestDto;

@Component
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = "select id, name, date, time from reservation";
        List<Reservation> reservations = jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        new Reservation(
                                new Id(rs.getLong("id")),
                                rs.getString("name"),
                                LocalDate.parse(rs.getString("date")),
                                LocalTime.parse(rs.getString("time"))
                        )
        );
        return reservations;
    }

    public long create(ReservationRequestDto newReservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            sql,
                            new String[]{"id"}
                    );
                    ps.setString(1, newReservation.name());
                    ps.setObject(2, newReservation.date());
                    ps.setObject(3, newReservation.time());
                    return ps;
                },
                keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    public void deleteById(Id id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(
                sql,
                id.getValue()
        );
    }
}

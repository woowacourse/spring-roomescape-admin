package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
@Primary
public class ReservationH2Repository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationH2Repository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long add(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, reservation.name());
            ps.setString(2, reservation.date().format(DateTimeFormatter.ISO_DATE));
            ps.setString(3, reservation.time().format(DateTimeFormatter.ofPattern("HH:mm")));
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update("DELETE FROM RESERVATION WHERE id = ?", id);
    }

    @Override
    public Map<Long, Reservation> findAllWithId() {
        String sql = "SELECT * FROM RESERVATION";
        return jdbcTemplate.query(sql, new ReservationAndIdExtractor());
    }
}

class ReservationAndIdExtractor implements ResultSetExtractor<Map<Long, Reservation>> {

    @Override
    public Map<Long, Reservation> extractData(ResultSet rs) throws SQLException {
        Map<Long, Reservation> reservationsMap = new HashMap<>();
        while (rs.next()) {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            String date = rs.getString("date");
            String time = rs.getString("time");

            Reservation reservation = new Reservation(name, LocalDate.parse(date), LocalTime.parse(time));
            reservationsMap.put(id, reservation);
        }
        return reservationsMap;
    }
}

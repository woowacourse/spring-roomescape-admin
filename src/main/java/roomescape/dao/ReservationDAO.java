package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long createReservation(ReservationRequest reservationRequest) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationRequest.name());
            ps.setString(2, reservationRequest.date());
            ps.setLong(3, reservationRequest.timeId());

            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Reservation> readReservations() {
        RowMapper<Reservation> rowMapper = (rs, rowNum) -> {
            Reservation reservation = new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("date"),
                    rs.getLong("time_id"),
                    rs.getString("time_value")
            );
            return reservation;
        };

        String sql = """
                    SELECT
                        r.id as reservation_id,
                        r.name,
                        r.date,
                        t.id as time_id,
                        t.start_at as time_value
                    FROM reservation as r INNER JOIN reservation_time as t ON r.time_id = t.id
        """;
        List<Reservation> reservations = jdbcTemplate.query(sql, rowMapper);
        return reservations;
    }

    public void deleteById(Long id) {
        String sql = "delete from reservation where id = ?";

        jdbcTemplate.update(sql, id);
    }
}

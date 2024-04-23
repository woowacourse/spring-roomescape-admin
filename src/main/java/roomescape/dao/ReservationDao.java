package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Name;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationDate;
import roomescape.domain.reservation.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class ReservationDao {
    private JdbcTemplate jdbcTemplate;
    private final StringBuilder stringBuilder = new StringBuilder();

    private final RowMapper<Reservation> reservationRowMapper = ((rs, rowNum) -> {
        Long reservationId = rs.getLong("reservation_id");
        String name = rs.getString("name");
        ReservationDate date = new ReservationDate(rs.getString("date"));
        ReservationTime time = new ReservationTime(rs.getLong("time_id"), rs.getString("time_value"));
        return new Reservation(reservationId, new Name(name), date, time);
    });

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAllReservation() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value" +
                " FROM reservation as r" +
                " inner join reservation_time as t" +
                " on r.time_id = t.id";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Reservation findById(Long id) {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value" +
                " FROM reservation as r" +
                " inner join reservation_time as t" +
                " on r.time_id = t.id" +
                " WHERE r.id = ?";
        return jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
    }

    public Long addReservation(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation (name, date, time_id) values (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, reservation.getName().getName());
            ps.setString(2, reservation.getDate().getDate().toString());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void deleteReservation(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

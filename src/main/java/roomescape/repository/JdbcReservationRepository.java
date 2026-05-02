package roomescape.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> getAll() {
        return jdbcTemplate.query(
            "SELECT "
                + "r.id as reservation_id, "
                + "r.name, "
                + "r.date, "
                + "t.id as time_id, "
                + "t.start_at as time_value "
                + "FROM reservation as r "
                + "INNER JOIN reservation_time as t "
                + "ON r.time_id = t.id",
            reservationRowMapper);
    }

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) ->
        new Reservation(
            rs.getLong("reservation_id"),
            rs.getString("name"),
            rs.getDate("date").toLocalDate(),
            new ReservationTime(
                rs.getLong("time_id"),
                rs.getTime("time_value").toLocalTime())
        );


    @Override
    public Reservation save(Reservation reservation) {
        final String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.name());
            ps.setDate(2, Date.valueOf(reservation.date()));
            ps.setLong(3, reservation.getTimeId());
            return ps;
        }, keyHolder);

        return reservation.withId(keyHolder.getKey().longValue());
    }

    @Override
    public void deleteById(long reservationId) {
        int update = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", reservationId);
        if (update == 0) {
            throw new NoSuchElementException("존재하지 않는 예약 아이디 입니다. reservationId: " + reservationId);
        }
    }

    @Override
    public boolean existByReservationTimeId(long reservationTimeId) {
        int count = jdbcTemplate.queryForObject(
            "SELECT "
                + "COUNT(1) "
                + "FROM reservation "
                + "WHERE time_id = ? ",
            Integer.class,
            reservationTimeId);
        return count != 0;
    }
}

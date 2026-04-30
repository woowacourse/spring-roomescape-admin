package roomescape.reservation.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.Reservation;
import roomescape.reservation.time.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class JdbcReservationRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select r.id as reservation_id, r.name, r.date, rt.id as time_id, rt.start_at" +
                " from reservation r " +
                "join reservation_time rt on r.time_id = rt.id";
        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> Reservation.of(
                        resultSet.getLong("reservation_id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        ReservationTime.of(
                                resultSet.getLong("time_id"),
                                resultSet.getString("start_at")
                        )
                ));
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "insert into reservation(name, date, time_id) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        long reservationId = keyHolder.getKey().longValue();

        return Reservation.of(reservationId, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}

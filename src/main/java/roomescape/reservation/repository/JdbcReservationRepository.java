package roomescape.reservation.repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.entity.ReservationTime;
import roomescape.reservation.payload.ReservationRequest;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(ReservationRequest request) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, request.name());
            ps.setObject(2, request.date());
            ps.setLong(3, request.timeId());
            return ps;
        }, keyHolder);

        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return findById(id);
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
                SELECT
                    r.id AS reservation_id,
                    r.name,
                    r.date,
                    t.id AS time_id,
                    t.start_at
                FROM reservation r
                INNER JOIN reservation_time t
                    ON r.time_id = t.id
                ORDER BY r.id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                Reservation.of(
                        rs.getLong("reservation_id"),
                        rs.getString("name"),
                        rs.getObject("date", LocalDate.class),
                        ReservationTime.of(
                                rs.getLong("time_id"),
                                rs.getObject("start_at", LocalTime.class)
                        )
                )
        );
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from reservation where id = ?";

        int affectedRows = jdbcTemplate.update(sql, id);
        if (affectedRows == 0) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다. id=" + id);
        }
    }

    private Reservation findById(Long id) {
        String sql = """
                SELECT
                    r.id AS reservation_id,
                    r.name,
                    r.date,
                    t.id AS time_id,
                    t.start_at
                FROM reservation r
                INNER JOIN reservation_time t
                    ON r.time_id = t.id
                WHERE r.id = ?
                """;
        
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                Reservation.of(
                        rs.getLong("reservation_id"),
                        rs.getString("name"),
                        rs.getObject("date", LocalDate.class),
                        ReservationTime.of(
                                rs.getLong("time_id"),
                                rs.getObject("start_at", LocalTime.class)
                        )
                ), id);
    }

}

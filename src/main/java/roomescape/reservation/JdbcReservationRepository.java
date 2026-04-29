package roomescape.reservation;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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
        return Reservation.of(id, request.name(), request.date(), request.timeId());
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select id, name, date, time_id from reservation";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                Reservation.of(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getObject("date", LocalDate.class),
                        rs.getLong("time_id")
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

}

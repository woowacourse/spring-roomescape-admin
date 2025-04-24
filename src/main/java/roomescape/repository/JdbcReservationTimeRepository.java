package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;

@Repository
public class JdbcReservationTimeRepository implements ReservationTimeRepository {

    private static final RowMapper<ReservationTime> rowMapper = ((rs, rowNum) -> {
        String startAt = rs.getString("start_at");
        LocalTime reservationStartAt = LocalTime.parse(startAt);
        ReservationTime reservationTime = new ReservationTime(rs.getLong("id"), reservationStartAt);
        return reservationTime;
    });

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        LocalTime startAt = reservationTime.startAt();

        String sql = "insert into reservation_time (start_at) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, startAt.toString());
            return ps;
        }, keyHolder);
        Long id = keyHolder.getKey().longValue();
        return new ReservationTime(id, startAt);
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "select * from reservation_time";

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public ReservationTime findById(Long id) {
        String sql = "select * from reservation_time where id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from reservation_time where id = ?";
        int affectedRows = jdbcTemplate.update(sql, id);

        if (affectedRows == 0) {
            throw new IllegalStateException("Reservation time with id " + id + " not found");
        }
    }
}

package roomescape.reservationtime;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class ReservationTimeUpdatingDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeUpdatingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Long id, ReservationTime reservationTime) {
        String sql = "update reservation_time SET start_at = ? where id = ?";
        jdbcTemplate.update(sql, reservationTime.getStartAt(), id);
    }

    public int delete(Long id) {
        String sql = "delete from reservation_time where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public Long insert(ReservationTime reservationTime) {
        String sql = "insert into reservation_time(start_at) values (?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setObject(1, reservationTime.getStartAt().toString());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return id;
    }
}

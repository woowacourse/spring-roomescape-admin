package roomescape.repositiory;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeRepository implements GeneralRepository<ReservationTime> {

    private final JdbcTemplate jdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    @Autowired
    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationTime> findAll() {
        String query = "select id, start_at from reservation_time";
        return jdbcTemplate.query(query, (rs, rowNum) -> new ReservationTime(
                rs.getLong("id"),
                rs.getObject("start_at", LocalTime.class)
        ));
    }

    @Override
    public ReservationTime findById(Long id) {
        String query = "select id, start_at from reservation_time where id = ?";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> new ReservationTime(
                rs.getLong("id"),
                rs.getObject("start_at", LocalTime.class)), id);
    }

    @Override
    public Long add(ReservationTime reservationTime) {
        String query = "insert into reservation_time (start_at) values (?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    query, new String[]{"id"});
            ps.setObject(1, reservationTime.getStartAt());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        reservationTime.setId(id);
        return id;
    }

    @Override
    public void delete(Long id) {
        String query = "delete from reservation_time where id=?";
        jdbcTemplate.update(query, id);
    }
}

package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeAddRequest;

@Repository
public class ReservationTimeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private RowMapper<ReservationTime> rowMapper = ((rs, rowNum) -> new ReservationTime(
            rs.getLong("id"),
            rs.getTime("start_at").toLocalTime()
    ));

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("select * from reservation_time", rowMapper);
    }

    public ReservationTime insert(ReservationTimeAddRequest reservationTimeAddRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation_time (start_at) values (?)",
                    new String[]{"id"}
            );
            ps.setString(1, reservationTimeAddRequest.getStartAt().toString());
            return ps;
        }, keyHolder);
        Long id = keyHolder.getKey().longValue();

        return findById(id);
    }

    public ReservationTime findById(Long id) {
        String sql = "select * from reservation_time where id = ?";
        ReservationTime reservationTime = jdbcTemplate.queryForObject(sql, rowMapper, id);
        return reservationTime;
    }

    public int removeById(Long id) {
        return jdbcTemplate.update("delete from reservation_time where id = ?", id);
    }
}

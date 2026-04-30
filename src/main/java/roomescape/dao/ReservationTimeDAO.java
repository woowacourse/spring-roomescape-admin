package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationTimeDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long createReservationTime(ReservationTime reservationTime) {
        String sql = "insert into reservation_time (start_at) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt());

            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<ReservationTime> readReservationTimes() {
        RowMapper<ReservationTime> rowMapper = (rs, rowNum) -> {
            ReservationTime reservationTime = new ReservationTime(
                    rs.getLong("id"),
                    rs.getString("start_at")
            );
            return reservationTime;
        };

        String sql = "select * from reservation_time";
        List<ReservationTime> reservationTimes = jdbcTemplate.query(sql, rowMapper);

        return reservationTimes;
    }

    public void deleteById(Long id) {
        String sql = "delete from reservation_time where id = ?";

        jdbcTemplate.update(sql, id);
    }

    public ReservationTime findById(Long id) {
        String sql = "select id, start_at from reservation_time where id = ?";

        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> {
                    ReservationTime reservationTime = new ReservationTime(
                            rs.getLong("id"),
                            rs.getString("start_at")
                    );
                    return reservationTime;
                }, id);
    }
}

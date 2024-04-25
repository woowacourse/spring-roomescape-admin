package roomescape.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationTimeDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Long save(ReservationTime request) {
        String query = "INSERT into reservation_time(start_at) VALUES(?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    query,
                    new String[]{"id"});
            ps.setString(1, request.startAt());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<ReservationTime> readAll() {
        String query = "SELECT * FROM reservation_time";
        List<ReservationTime> reservationTimes = jdbcTemplate.query(query,
                (rs, rowNum) -> {
                    return new ReservationTime(
                            rs.getLong("id"),
                            rs.getString("start_at"));
                }
        );
        return reservationTimes;
    }

    public void delete(Long id) {
        String query = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(query, id);
    }
}

package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;

@Repository
public class ReservationTimeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<ReservationTime> actorRowMapper = (resultSet, rowNum) -> {
        ReservationTime reservationTime = new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getTime("startAt").toLocalTime()
        );
        return reservationTime;
    };

    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM startAt";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    public Long saveTime(ReservationTime reservationTime) {
        String sql = "INSERT INTO startAt (startAt) values(?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setTime(3, java.sql.Time.valueOf(reservationTime.getStartAt()));
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deleteTimeById(Long id) {
        String sql = "DELETE FROM startAt WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.TimeCreateRequestDto;
import roomescape.model.ReservationTime;

@Repository
public class ReservationTimeDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationTimeDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<ReservationTime> findAllTimes() {
        return jdbcTemplate.query("SELECT * FROM reservation_time", rowMapper);
    }

    private final RowMapper<ReservationTime> rowMapper = (resultSet, rowNum) -> new ReservationTime(
            resultSet.getLong("id"),
            resultSet.getString("start_at"));

    public ReservationTime insert(TimeCreateRequestDto timeCreateRequestDto) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql, new String[]{"id"});
            ps.setString(1, timeCreateRequestDto.startAt());
            return ps;
        }, keyHolder);
        return new ReservationTime(keyHolder.getKey().longValue(), timeCreateRequestDto.startAt());
    }

    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
    }
}

package roomescape.time.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.time.dto.TimeRequestDto;
import roomescape.time.entity.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TimeDao {

    private final JdbcTemplate jdbcTemplate;

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime insert(TimeRequestDto requestDto) {
        String sql = "insert into reservation_time (start_at) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, requestDto.getStartAt());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new ReservationTime(id, requestDto.getStartAt());
    }

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("select id, start_at from reservation_time",
                (rs, rowNum) -> new ReservationTime(rs.getLong("id"), rs.getString("start_at")));
    }

    public void delete(Long id) {
        jdbcTemplate.update("delete from reservation_time where id = ?", id);
    }
}

package roomescape.time.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.time.dto.TimeRequestDto;
import roomescape.time.entity.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class JdbcReservationTimeRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ReservationTime> reservationTimeRowMapper = (resultSet, rowNum) ->
            new ReservationTime(
                resultSet.getLong("time_id"),
                resultSet.getString("start_at")
    );

    public JdbcReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime save(TimeRequestDto requestDto) {
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

    public void delete(Long id) {
        jdbcTemplate.update("delete from reservation_time where id = ?", id);
    }

    public ReservationTime findById(Long id) {
        String sql = "select id, start_at from reservation_time where id = ?";
        return jdbcTemplate.queryForObject(sql, reservationTimeRowMapper, id);
    }

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("select id, start_at from reservation_time", reservationTimeRowMapper);
    }
}

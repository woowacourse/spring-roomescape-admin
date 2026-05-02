package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime insert(ReservationTimeRequest reservationTimeRequest) {
        final String sql = "insert into reservation_time (start_at) values(?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql,new String[]{"id"});
            ps.setString(1, reservationTimeRequest.getStartAt());
            return ps;
        }, keyHolder);
        return new ReservationTime(keyHolder.getKey().longValue(), reservationTimeRequest.getStartAt());
    }

    public List<ReservationTime> select() {
        final String sql = "select id, start_at from reservation_time;";
        return jdbcTemplate.query(sql,rowMapper());
    }

    public void delete(long id) {
        final String sql = "delete from reservation_time where id = ?;";
        jdbcTemplate.update(sql, id);
    }

    public ReservationTime selectById(long id) {
        final String sql = "select * from reservation_time where id = ?;";
        return jdbcTemplate.queryForObject(sql, rowMapper(), id);
    }

    private RowMapper<ReservationTime> rowMapper() {
        return (rs, rowNum) -> new ReservationTime(rs.getLong("id"), rs.getString("start_at"));
    }
}

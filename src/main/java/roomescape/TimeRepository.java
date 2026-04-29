package roomescape;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class TimeRepository {
    private static final RowMapper<ReservationTime> RESERVATION_TIME_ROW_MAPPER = (resultSet, rowNum) ->
            new ReservationTime(resultSet.getLong("id"), resultSet.getString("start_at"));

    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime save(String time) {
        String sql = "insert into reservation_time(start_at) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement pstmt = connection.prepareStatement(sql, new String[]{"id"});
            pstmt.setString(1, time);
            return pstmt;
        }, keyHolder);

        return new ReservationTime(keyHolder.getKey().longValue(), time);
    }

    public Optional<ReservationTime> findById(long id) {
        String sql = "select id, start_at from reservation_time where id = ?";
        List<ReservationTime> result = jdbcTemplate.query(sql, RESERVATION_TIME_ROW_MAPPER, id);
        return result.stream().findFirst();
    }

    public List<ReservationTime> findAll() {
        String sql = "select id, start_at from reservation_time";

        return jdbcTemplate.query(sql, RESERVATION_TIME_ROW_MAPPER);
    }

    public void delete(long id) {
        String sql = "delete from reservation_time where id = ?";

        jdbcTemplate.update(sql, id);
    }


}

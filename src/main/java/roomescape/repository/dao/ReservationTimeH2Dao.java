package roomescape.repository.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.domain.ReservationTime;

@Component
@RequiredArgsConstructor
public class ReservationTimeH2Dao {

    private static final RowMapper<ReservationTime> ROW_MAPPER = (resultSet, rowNum) -> new ReservationTime(
            resultSet.getLong("id"),
            resultSet.getTime("start_at").toLocalTime()
    );

    private final JdbcTemplate jdbcTemplate;

    public List<ReservationTime> selectAll() {
        String selectQuery = """
                SELECT id, start_at
                FROM reservation_time
                """;

        return jdbcTemplate.query(selectQuery, ROW_MAPPER);
    }

    public ReservationTime insertAndGet(ReservationTime reservationTime) {
        String insertQuery = "INSERT INTO reservation_time (start_at) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertQuery, new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt().toString());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new ReservationTime(id, reservationTime.getStartAt());
    }

    public Optional<ReservationTime> selectById(Long id) {
        String selectQuery = """
                SELECT id, start_at
                FROM reservation_time
                WHERE id = ?
                """;

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(selectQuery, ROW_MAPPER, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteById(Long id) {
        String deleteQuery = "DELETE FROM reservation_time WHERE id = ?";

        jdbcTemplate.update(deleteQuery, id);
    }
}

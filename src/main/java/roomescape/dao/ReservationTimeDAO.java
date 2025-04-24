package roomescape.dao;

import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationTimeReqDto;
import roomescape.dto.ReservationTimeResDto;

@Repository
public class ReservationTimeDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTimeResDto> findAllReservationTimes() {
        return jdbcTemplate.query("SELECT * FROM reservation_time", (resultSet, rowNum) -> new ReservationTimeResDto(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        ));
    }

    public ReservationTimeResDto addAndGet(ReservationTimeReqDto dto) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingColumns("start_at")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = Map.of("start_at", dto.startAt());
        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);

        return new ReservationTimeResDto(id.longValue(), dto.startAt());
    }

    public void deleteById(Long id) {
        int rows = jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
        if (rows == 0) {
            throw new EmptyResultDataAccessException(rows);
        }
    }
}

package roomescape.dao;

import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationReqDto;
import roomescape.dto.ReservationResDto;
import roomescape.dto.ReservationTimeReqDto;
import roomescape.dto.ReservationTimeResDto;

@Repository
public class UpdatingDAO {

    private final JdbcTemplate jdbcTemplate;

    public UpdatingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationResDto addAndGet(ReservationReqDto dto) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingColumns("name", "date", "time")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = Map.of(
                "name", dto.name(),
                "date", dto.date(),
                "time", dto.time()
        );
        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);

        return new ReservationResDto(id.longValue(), dto.name(), dto.date(), dto.time());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }

    public ReservationTimeResDto addAndGet2(ReservationTimeReqDto dto) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingColumns("start_at")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = Map.of("start_at", dto.startAt());
        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);

        return new ReservationTimeResDto(id.longValue(), dto.startAt());
    }
}

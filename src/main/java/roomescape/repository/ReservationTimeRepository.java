package roomescape.repository;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationTimeReadDto;
import roomescape.model.ReservationTime;

@Repository
public class ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public ReservationTimeReadDto add(ReservationTime time) {
        Map<String, LocalTime> params = new HashMap<>();
        params.put("start_at", time.getStartAt());

        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return new ReservationTimeReadDto(id, time.getStartAt());
    }

    public List<ReservationTimeReadDto> findAll() {
        String sql = "select * from reservation_time";
        List<ReservationTimeReadDto> dtos = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    ReservationTimeReadDto dto = new ReservationTimeReadDto(
                            resultSet.getLong("id"),
                            resultSet.getTime("start_at").toLocalTime()
                    );
                    return dto;
                }
        );
        return dtos;
    }
}

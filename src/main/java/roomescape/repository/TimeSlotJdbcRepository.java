package roomescape.repository;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.TimeSlot;
import roomescape.repository.rowmapper.TimeSlotRowMapper;
import roomescape.service.dto.TimeSlotDto;

@Repository
public class TimeSlotJdbcRepository implements TimeSlotRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public TimeSlotJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time_slot")
                .usingColumns("start_at")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public TimeSlot create(TimeSlotDto timeSlotDto) {
        Map<String, ? extends Serializable> parameters = Map.of(
                "start_at", timeSlotDto.getTime()
        );
        Number key = jdbcInsert.executeAndReturnKey(parameters);
        return timeSlotDto.toEntity(key.longValue());
    }

    @Override
    public List<TimeSlot> findAll() {
        String sql = "select id, start_at from time_slot";
        return jdbcTemplate.query(sql, TimeSlotRowMapper.getInstance());
    }

    @Override
    public Optional<TimeSlot> findById(Long id) {
        String sql = "select id, start_at from time_slot where id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, TimeSlotRowMapper.getInstance(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from time_slot where id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsByTime(LocalTime time) {
        String sql = "select count(*) from time_slot where start_at = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, time) > 0;
    }

    @Override
    public void deleteAll() {
        String sql = "delete from time_slot";
        jdbcTemplate.update(sql);
    }
}

package roomescape.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationTimeDto;

@Repository
public class ReservationTimeJdbcRepository implements ReservationTimeRepository {

    private static final RowMapper<ReservationTime> ROW_MAPPER = (resultSet, rowNum) ->
            new ReservationTime(
                    Long.valueOf(resultSet.getString("id")),
                    resultSet.getString("start_at")
            );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationTimeJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingColumns("start_at")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public ReservationTime create(ReservationTimeDto reservationTimeDto) {
        Map<String, String> parameters = Map.of(
                "start_at", reservationTimeDto.getTime()
        );
        Number key = jdbcInsert.executeAndReturnKey(parameters);
        return reservationTimeDto.toEntity(key.longValue());
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "select id, start_at from reservation_time";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        String sql = "select id, start_at from reservation_time where id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, ROW_MAPPER, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void deleteAll() {
        String sql = "delete from reservation_time";
        jdbcTemplate.update(sql);
    }
}

package roomescape.time.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.ReservationTimeRequestDto;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReservationTimeDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<ReservationTime> rowMapper;

    public ReservationTimeDao(final JdbcTemplate jdbcTemplate, final DataSource dataSource, final RowMapper<ReservationTime> rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("RESERVATION_TIME")
                .usingGeneratedKeyColumns("id");
        this.rowMapper = rowMapper;
    }

    public long save(final ReservationTimeRequestDto requestDto) {
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("start_at", requestDto.startAt());
        return simpleJdbcInsert.executeAndReturnKey(params)
                               .longValue();
    }

    public List<ReservationTime> findAll() {
        final String sql = "select * from reservation_time";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> rowMapper.mapRow(resultSet, rowNum));
    }

    public int deleteById(final Long id) {
        final String sql = "delete from reservation_time where id = ?";
        return jdbcTemplate.update(sql, Long.valueOf(id));
    }
}

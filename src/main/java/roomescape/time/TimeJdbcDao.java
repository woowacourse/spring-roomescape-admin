package roomescape.time;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class TimeJdbcDao implements TimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeJdbcDao(
            @Autowired JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }


    @Override
    public Long saveTime(final Time time) {
        final SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(time);
        final Number id = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);
        return id.longValue();
    }

    @Override
    public List<Time> findAllTime() {
        final String sql = "SELECT * FROM RESERVATION_TIME";

        return jdbcTemplate.query(sql, timeMapper());
    }

    @Override
    public Time findTimeById(final Long id) {
        final String sql = "SELECT * FROM RESERVATION_TIME WHERE id=?";
        return jdbcTemplate.queryForObject(sql, timeMapper(), id);
    }

    private RowMapper<Time> timeMapper() {
        return (resultSet, rowNum) -> {
            return new Time(
                    resultSet.getLong("id"),
                    resultSet.getTime("start_at").toLocalTime()
            );
        };
    }

    @Override
    public void deleteTimeById(final Long id) {
        final String sql = "DELETE FROM RESERVATION_TIME WHERE id=?";
        final int updatedCount = jdbcTemplate.update(sql, id);
        validateUpdateSuccess(updatedCount);
    }

    private void validateUpdateSuccess(final int updatedCount) {
        if (updatedCount == 0) {
            throw new IllegalArgumentException("수정/삭제된 Time이 존재하지 않습니다.");
        }
    }
}

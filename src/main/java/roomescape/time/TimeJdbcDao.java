package roomescape.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class TimeJdbcDao implements TimeDao{

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeJdbcDao(
            @Autowired JdbcTemplate jdbcTemplate
    ){
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }


    @Override
    public Time saveTime(final Time time) {
        final SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(time);
        final Number id = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);
        return time.writeId(id.longValue());
    }
}

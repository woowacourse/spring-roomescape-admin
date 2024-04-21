package roomescape.times;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TimesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long add(Times times) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(times);
        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
    }

    public List<Times> findAll() {
        String SQL = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(SQL, (rs, rowNum) -> {
            Times times = new Times(
                    rs.getLong("id"),
                    rs.getString("start_at")
            );
            return times;
        });
    }
}

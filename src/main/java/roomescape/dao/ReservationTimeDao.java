package roomescape.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeCreateRequest;

import javax.sql.DataSource;
import java.time.LocalTime;
import java.util.List;

import static roomescape.dao.Table.RESERVATION_TIME;

@Repository
public class ReservationTimeDao {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(RESERVATION_TIME.getName())
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<ReservationTime> timeRowMapper = (resultSet, rowNum) -> new ReservationTime(
            resultSet.getLong("id"),
            LocalTime.parse(resultSet.getString("start_at"))
    );

    public List<ReservationTime> findAll() {
        String sql = "select * from reservation_time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public ReservationTime findById(long id) {
        String sql = "select * from reservation_time where id=?";
        try {
            return jdbcTemplate.queryForObject(sql, timeRowMapper, id);
        }catch(EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("해당 id는 reservation_time에 존재하지 않습니다");
        }
    }

    public ReservationTime insert(ReservationTimeCreateRequest reservationTimeCreateRequest) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(reservationTimeCreateRequest);
        long id = simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
        return reservationTimeCreateRequest.toTime(id);
    }

    public void delete(long id) {
        String sql = "delete from reservation_time where id=?";
        jdbcTemplate.update(sql, id);
    }
}

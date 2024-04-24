package roomescape.reservation.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequestDto;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Reservation> rowMapper;

    public ReservationDao(final JdbcTemplate jdbcTemplate, final DataSource dataSource, final RowMapper<Reservation> rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
        this.rowMapper = rowMapper;
    }

    public List<Reservation> findAll() {
        final String sql = "SELECT \n" +
                "    r.id as reservation_id, \n" +
                "    r.name, \n" +
                "    r.date, \n" +
                "    t.id as time_id, \n" +
                "    t.start_at as time_value \n" +
                "FROM reservation as r \n" +
                "inner join reservation_time as t \n" +
                "on r.time_id = t.id";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> rowMapper.mapRow(resultSet, rowNum));
    }

    public Reservation findById(final Long id) {
        final String sql = "SELECT \n" +
                "    r.id as reservation_id, \n" +
                "    r.name, \n" +
                "    r.date, \n" +
                "    t.id as time_id, \n" +
                "    t.start_at as time_value \n" +
                "FROM reservation as r \n" +
                "inner join reservation_time as t \n" +
                "on r.time_id = t.id and r.id = ?";
        return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> rowMapper.mapRow(resultSet, rowNum), id);
    }

    public long save(final ReservationRequestDto requestDto) {
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", requestDto.name())
                .addValue("date", requestDto.date())
                .addValue("time_id", requestDto.timeId());
        return simpleJdbcInsert.executeAndReturnKey(params)
                               .longValue();
    }

    public int deleteById(final Long id) {
        final String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, Long.valueOf(id));
    }
}

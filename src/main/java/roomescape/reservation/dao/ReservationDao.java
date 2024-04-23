package roomescape.reservation.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.time.domain.ReservationTime;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDao(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        final Reservation reservation = new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        resultSet.getString("time_value")
                )
        );
        return reservation;
    };

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
        return jdbcTemplate.query(sql, reservationRowMapper);
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
        return jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
    }

    public long create(final ReservationRequestDto requestDto) {
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

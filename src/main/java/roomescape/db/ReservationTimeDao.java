package roomescape.db;


import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationTimeDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public ReservationTime save(ReservationTime reservationTime) {
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(reservationTime);
        long id = jdbcInsert.executeAndReturnKey(param).longValue();
        return new ReservationTime(id, reservationTime.getStartAt());
    }

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("select id, start_at from reservation_time",
                (resultSet, rowNum) -> new ReservationTime(resultSet.getLong("id"),
                        LocalTime.parse(resultSet.getString("start_at"))));
    }

    public ReservationTime findById(final Long id) {
        return jdbcTemplate.queryForObject("select id, start_at from reservation_time where id=?",
                (resultSet, rowNum) -> new ReservationTime(resultSet.getLong("id"),
                        LocalTime.parse(resultSet.getString("start_at"))), id);
    }


    public void deleteById(final long id) {
        jdbcTemplate.update("delete from reservation_time where id=?", id);
    }
}

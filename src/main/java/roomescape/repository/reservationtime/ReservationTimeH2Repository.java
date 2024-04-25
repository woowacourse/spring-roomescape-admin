package roomescape.repository.reservationtime;

import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
@Primary
public class ReservationTimeH2Repository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationTimeH2Repository(JdbcTemplate jdbcTemplate, DataSource source) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(source)
                .withTableName("RESERVATION_TIME")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(reservationTime);
        long id = jdbcInsert.executeAndReturnKey(params).longValue();

        return ReservationTime.of(id, reservationTime);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
    }

    @Override
    public ReservationTime findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM reservation_time WHERE id = ?",
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getLong("id"),
                        LocalTime.parse(resultSet.getString("start_at"))
                ),
                id
        );
    }

    @Override
    public List<ReservationTime> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM reservation_time",
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getLong("id"),
                        LocalTime.parse(resultSet.getString("start_at"))
                )
        );
    }
}

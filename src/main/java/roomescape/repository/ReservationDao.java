package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;

    public ReservationDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String sql = """
                SELECT a.id AS reservation_id, a.name AS name, a.date AS date, t.id AS time_id, t.start_at AS start_at
                FROM reservation AS a
                LEFT JOIN reservation_time AS t ON a.time_id = t.id""";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            ReservationTime reservationTime = new ReservationTime(resultSet.getLong("time_id"),
                    LocalTime.parse(resultSet.getString("start_at")));

            return new Reservation(resultSet.getLong("reservation_id"), resultSet.getString("name"),
                    LocalDate.parse(resultSet.getString("date")), reservationTime);
        });
    }

    public void save(Reservation reservation) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(reservation);
        Number key = jdbcInsert.executeAndReturnKey(parameterSource);

        reservation.setId(key.longValue());
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void deleteByTimeId(Long timeId) {
        String sql = "DELETE FROM reservation WHERE time_id = ?";
        jdbcTemplate.update(sql, timeId);
    }
}

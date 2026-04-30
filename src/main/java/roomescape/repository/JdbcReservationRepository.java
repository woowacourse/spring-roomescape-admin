package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Reservation> findAll() {
        String sql = "" +
                "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.start_at AS time_value " +
                "FROM reservation AS r " +
                "INNER JOIN reservation_time AS t " +
                "ON r.time_id = t.id";

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("reservation_id"),
                        resultSet.getString("name"),
                        resultSet.getObject("date", LocalDate.class),
                        new ReservationTime(resultSet.getLong("time_id"),
                                resultSet.getObject("time_value", LocalTime.class))
                )
        );
    }

    @Override
    public Reservation save(Reservation reservation) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
        long generatedKey = simpleJdbcInsert
                .executeAndReturnKey(new BeanPropertySqlParameterSource(reservation))
                .longValue();

        return new Reservation(
                generatedKey,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

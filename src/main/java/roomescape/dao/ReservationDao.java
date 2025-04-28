package roomescape.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationDateTime;
import roomescape.domain.ReservationName;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert reservationInsert;

    public ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String sql = """
                SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value
                FROM reservation as r inner join reservation_time as t on r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                new ReservationName(resultSet.getString("name")),
                new ReservationDate(resultSet.getString("date")),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        resultSet.getString("time_value")
                )
        ));
    }

    public Reservation save(final ReservationName name, final ReservationDateTime dateTime) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", name.getValue());
        parameters.put("date", dateTime.date());
        parameters.put("time_id", dateTime.reservationTime().id());

        Number newId = reservationInsert.executeAndReturnKey(parameters);

        return new Reservation(
                newId.longValue(),
                name,
                dateTime.reservationDate(),
                dateTime.reservationTime()
        );
    }

    public void deleteById(final Long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}

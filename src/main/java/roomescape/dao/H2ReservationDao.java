package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class H2ReservationDao implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public H2ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
                    SELECT
                        r.id as reservation_id,
                        r.name,
                        r.date,
                        t.id as time_id,
                        t.start_at as time_value
                    FROM reservation as r 
                    inner join reservation_time as t 
                    on r.time_id = t.id
                    """;

        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> {
                    Long reservationId = resultSet.getLong("reservation_id");
                    String name = resultSet.getString("name");
                    LocalDate date = resultSet.getObject("date", LocalDate.class);

                    Long timeId = resultSet.getLong("time_id");
                    LocalTime startAt = resultSet.getObject("time_value", LocalTime.class);
                    ReservationTime reservationTime = ReservationTime.of(timeId, startAt);

                    return Reservation.of(reservationId, name, date, reservationTime);
                });
    }

    @Override
    public Reservation insert(final Reservation reservation) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getCustomerName());
        parameters.put("date", reservation.getReservationDate());
        parameters.put("time_id", reservation.getReservationTime().getId());
        Number savedId = simpleJdbcInsert.executeAndReturnKey(parameters);

        return Reservation.of(savedId.longValue(), reservation.getCustomerName(), reservation.getReservationDate(), reservation.getReservationTime());
    }

    @Override
    public boolean deleteById(final Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int deletedRows = jdbcTemplate.update(sql, id);
        return deletedRows > 0;
    }
}

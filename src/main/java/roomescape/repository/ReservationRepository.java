package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation add(Reservation reservation) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", reservation.getName());
        params.put("date", reservation.getDate());
        params.put("time_id", reservation.getTime().getId());

        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public int deleteBy(Long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public List<Reservation> findAll() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value FROM reservation as r inner join reservation_time as t on r.time_id = t.id";
        List<Reservation> reservations = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("reservation_id"),
                            resultSet.getString("name"),
                            resultSet.getDate("date").toLocalDate(),
                            new ReservationTime(
                                    resultSet.getLong("time_id"),
                                    resultSet.getTime("time_value").toLocalTime())
                    );
                    return reservation;
                }
        );
        return reservations;
    }
}

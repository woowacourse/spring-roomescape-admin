package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;
import roomescape.model.ReservationWithTimeId;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDao(JdbcTemplate jdbcTemplate, ReservationTimeDao reservationTimeDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> selectAllReservation() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value FROM reservation as r inner join reservation_time as t on r.time_id = t.id";

        List<Reservation> reservations = jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Reservation reservation = new Reservation(resultSet.getLong("id"),
                    resultSet.getString("name"),
                    LocalDate.parse(resultSet.getString("date")),
                    new ReservationTime(resultSet.getLong("time_id"),
                            LocalTime.parse(resultSet.getString("time_value"))));

            return reservation;
        });

        return reservations;
    }

    public Long addReservation(ReservationWithTimeId reservation) {
        final Map<String, Object> parameters = Map.of(
                "name", reservation.getName(),
                "date", reservation.getDate(),
                "time_id", reservation.getTimeId());

        Long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();

        return id;
    }

    public int deleteReservationById(Long id) {
        String sql = "DELETE FROM reservation WHERE id=?";

        int effectedRow = jdbcTemplate.update(sql, id);

        return effectedRow;
    }
}

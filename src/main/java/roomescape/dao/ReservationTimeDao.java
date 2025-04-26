package roomescape.dao;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.ReservationTime;

@Repository
public class ReservationTimeDao implements ReservationTimeDaoInterface {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).usingGeneratedKeyColumns("id")
                .withTableName("reservation_time");
    }

    public ReservationTime addReservation(ReservationTime reservationTime) {
        Map<String, Object> map = Map.of("start_at", reservationTime.getTime().toString());
        Number number = simpleJdbcInsert.executeAndReturnKey(map);

        return new ReservationTime(number.longValue(), reservationTime.getTime());
    }

    public List<ReservationTime> getTimeReservations() {
        String sql = "SELECT * from reservation_time";
        List<ReservationTime> reservationTimes = jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            ReservationTime reservationTime = new ReservationTime(resultSet.getLong("id"),
                    LocalTime.parse(resultSet.getString("start_at")));

            return reservationTime;
        });

        return reservationTimes;
    }

    public int deleteTimeReservation(Long id) {
        String sql = "DELETE from reservation_time WHERE id = ?";
        int effectedRowCount = jdbcTemplate.update(sql,id);

        return effectedRowCount;
    }

    public ReservationTime findTimeById(Long reservationTimeId) {
        String sql = "SELECT id,start_at FROM reservation_time where id=?";
        ReservationTime reservationTime = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            return new ReservationTime(rs.getLong("id"), LocalTime.parse(rs.getString("start_at"))
            );
        }, reservationTimeId);

        return reservationTime;
    }
}

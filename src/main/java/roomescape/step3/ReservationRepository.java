package roomescape.step3;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component("ReservationRepositoryStep3")
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAllReservations() {
        String sql = "SELECT r.id, r.name, r.date, r.time_id, rt.start_at " +
                "FROM reservation_v2 r " +
                "JOIN reservation_time rt ON r.time_id = rt.id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    LocalDate.parse(rs.getString("date")),
                    new ReservationTime(
                            rs.getLong("time_id"),
                            LocalTime.parse(rs.getString("start_at"))));
        });
    }

    public Reservation saveReservation(ReservationRequest request) {
        String sql = "INSERT INTO reservation_v2(name, date, time_id) VALUES (?, ?, ?)";
        String sql2 = "SELECT id, start_at FROM reservation_time where id = ?";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                    ps.setString(1, request.name());
                    ps.setString(2, request.date().toString());
                    ps.setLong(3, request.timeId());
                    return ps;
                }, keyHolder);

        ReservationTime reservationTime = jdbcTemplate.queryForObject(sql2, (rs, rowNum) -> {
            return new ReservationTime(
                    rs.getLong("id"),
                    LocalTime.parse(rs.getString("start_at"))
            );
        }, request.timeId());

        long id = keyHolder.getKey().longValue();

        return new Reservation(
                id,
                request.name(),
                request.date(),
                reservationTime);
    }

    public void deleteReservationById(Long id) {
        String sql = "DELETE FROM reservation_v2 where id = ?";
        jdbcTemplate.update(sql, id);
    }


    public ReservationTime saveReservationTime(ReservationTimeRequest request) {
        String sql = "INSERT INTO reservation_time(start_at) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, request.startAt().toString());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();

        return new ReservationTime(id, request.startAt());
    }

    public List<ReservationTime> findAllReservationTimes() {
        String sql = "SELECT * FROM reservation_time";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return new ReservationTime(
                    rs.getLong("id"),
                    LocalTime.parse(rs.getString("start_at"))
            );
        });
    }

    public void deleteReservationTimeById(Long id) {
        String sql = "DELETE FROM reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }
}

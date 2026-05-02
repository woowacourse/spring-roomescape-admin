package roomescape.reservationtime.repository;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservationtime.domain.ReservationTime;

@Repository
@AllArgsConstructor
public class ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<ReservationTime> findAllReservationTimes() {
        String sql = "SELECT id, start_at FROM reservation_time";

        return jdbcTemplate.query(sql, (rs, rowNum) ->

                ReservationTime.builder()
                        .id(rs.getLong("id"))
                        .startAt(LocalTime.parse(rs.getString("start_at")))
                        .build()
//                new ReservationTime(
//                rs.getLong("id"),
//                LocalTime.parse(rs.getString("start_at"))
        );
    }

    public ReservationTime saveReservationTime(ReservationTime reservationTime) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES(?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt().toString());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();

        return ReservationTime.builder()
                .id(generatedId)
                .startAt(reservationTime.getStartAt())
                .build();
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public ReservationTime findById(Long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                ReservationTime.builder()
                        .id(rs.getLong("id"))
                        .startAt(LocalTime.parse(rs.getString("start_at")))
                        .build(), id);
    }
}

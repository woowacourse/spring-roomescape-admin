package roomescape.reservationtime.repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservationtime.domain.ReservationTime;

@Repository
@AllArgsConstructor
public class ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public static final RowMapper<ReservationTime> ROW_MAPPER = (rs, rowNum) ->
            ReservationTime.builder()
                    .id(rs.getLong("id"))
                    .startAt(rs.getTime("start_at").toLocalTime())
                    .build();

    public List<ReservationTime> findAllReservationTimes() {
        String sql = "SELECT id, start_at FROM reservation_time";

        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public ReservationTime saveReservationTime(ReservationTime reservationTime) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES(?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt().toString());
            return ps;
        }, keyHolder);

        Number generatedId = Optional.ofNullable(keyHolder.getKey())
                .orElseThrow(() -> new RuntimeException("[ERROR] DB 아이디 생성에 실패했습니다."));

        return ReservationTime.builder()
                .id(generatedId.longValue())
                .startAt(reservationTime.getStartAt())
                .build();
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public ReservationTime findById(Long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, ROW_MAPPER, id);
    }
}

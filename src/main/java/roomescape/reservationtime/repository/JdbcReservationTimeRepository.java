package roomescape.reservationtime.repository;

import java.sql.PreparedStatement;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservationtime.domain.ReservationTime;

@RequiredArgsConstructor
@Repository
public class JdbcReservationTimeRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ReservationTime findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, start_at FROM reservation_time WHERE id = ?",
                (rs, rowNum) -> ReservationTime.builder()
                        .id(rs.getLong("id"))
                        .startAt(rs.getTime("start_at").toLocalTime())
                        .build(),
                id
        );
    }

    @Override
    public List<ReservationTime> findAll() {
        return jdbcTemplate.query(
                "SELECT id, start_at FROM reservation_time",
                (rs, rowNum) -> ReservationTime.builder()
                        .id(rs.getLong("id"))
                        .startAt(rs.getTime("start_at").toLocalTime())
                        .build()
        );
    }

    @Override
    public Long save(ReservationTime reservationTime) {
        String formattedStartAt = reservationTime.getStartAt().format(DateTimeFormatter.ofPattern("HH:mm"));

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation_time (start_at) VALUES (?)",
                    new String[]{"id"});
            ps.setString(1, formattedStartAt);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
    }
}

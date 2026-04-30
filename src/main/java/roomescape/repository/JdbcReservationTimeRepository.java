package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class JdbcReservationTimeRepository implements ReservationTimeRepository {
    private static final RowMapper<ReservationTime> RESERVATION_ROW_MAPPER = (rs, rowNum) ->
            new ReservationTime(
                    rs.getLong("id"),
                    rs.getString("start_at")
            );

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("SELECT id, start_at FROM reservation_time", RESERVATION_ROW_MAPPER);
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation_time (start_at) VALUES (?)",
                    new String[]{"id"}
            );
            ps.setString(1, reservationTime.startAt());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        return new ReservationTime(
                generatedId,
                reservationTime.startAt()
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        List<ReservationTime> reservationTimes = jdbcTemplate.query("SELECT * FROM reservation_time WHERE id = ?",
                RESERVATION_ROW_MAPPER, id);
        return reservationTimes.stream().findFirst();
    }
}

package roomescape.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("SELECT id, start_at FROM reservation_time", RESERVATION_ROW_MAPPER);
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        Map<String, Object> params = Map.of(
                "start_at", reservationTime.startAt()
        );
        Number generatedKey = jdbcInsert.executeAndReturnKey(params);
        Long generatedId = generatedKey.longValue();
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

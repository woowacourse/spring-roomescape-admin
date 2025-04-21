package roomescape.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dto.CreateTimeSlotRequest;
import roomescape.model.ReservationTimeSlot;

@Repository
public class TimeSlotJdbcRepository implements TimeSlotRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TimeSlotJdbcRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<ReservationTimeSlot> findById(final long id) {
        String sql = "SELECT * FROM RESERVATION_TIME WHERE id = ?";
        final var timeSlots = jdbcTemplate.query(
            sql,
            (rs, rowNum) -> {
                final var savedId = rs.getLong("id");
                final var startAt = rs.getString("startAt");
                return new ReservationTimeSlot(savedId, LocalTime.parse(startAt));
            }
        );

        return timeSlots.stream().findAny();
    }

    @Override
    public long save(final CreateTimeSlotRequest request) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);

        final var generatedKey = insertActor.withTableName("reservation_time")
            .usingGeneratedKeyColumns("id")
            .executeAndReturnKey(Map.of(
                "start_at", request.startAt()
            ));
        return generatedKey.longValue();
    }

    @Override
    public boolean removeById(final long id) {
        final var removedRowsCount = jdbcTemplate.update("delete from RESERVATION_TIME where id = ?", id);
        return removedRowsCount > 0;
    }

    @Override
    public List<ReservationTimeSlot> getTimeSlots() {
        String sql = "SELECT * FROM RESERVATION_TIME";
        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> {
                final var savedId = rs.getLong("id");
                final var startAt = rs.getString("start_at");
                return new ReservationTimeSlot(savedId, LocalTime.parse(startAt));
            }
        );
    }
}

package roomescape.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.Reservation;
import roomescape.ReservationRequest;

@Repository
public class ReservationJdbcRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationJdbcRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Reservation> findById(final long id) {
        final var reservations = jdbcTemplate.query(
            "select * from RESERVATION where id = ?",
            (rs, rowNum) -> {
                final var name = rs.getString("name");
                final var date = rs.getDate("date").toLocalDate();
                final var time = rs.getTime("time").toLocalTime();
                return new Reservation(id, name, date, time);
            },
            id
        );
        return reservations.stream().findAny();
    }

    public long save(ReservationRequest request) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        final var generatedKey = insertActor.withTableName("RESERVATION")
            .usingGeneratedKeyColumns("id")
            .executeAndReturnKey(Map.of(
                "name", request.name(),
                "date", request.date(),
                "time", request.time()
            ));
        return generatedKey.longValue();
    }

    public boolean removeById(long id) {
        final var removedRowsCount = jdbcTemplate.update("delete from RESERVATION where id = ?", id);
        return removedRowsCount > 0;
    }

    public List<Reservation> getReservations() {
        return jdbcTemplate.query("select * from RESERVATION", (rs, rowNum) -> {
            final var id = rs.getLong("id");
            final var name = rs.getString("name");
            final var date = rs.getDate("date").toLocalDate();
            final var time = rs.getTime("time").toLocalTime();
            return new Reservation(id, name, date, time);
        });
    }
}

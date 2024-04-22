package roomescape.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.repository.rowmapper.ReservationRowMapper;

@Repository
public class ReservationJdbcRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingColumns("name", "date", "time_slot_id")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Reservation addReservation(Reservation reservation) {
        Map<String, ? extends Serializable> parameters = Map.of(
                "name", reservation.getName().asText(),
                "date", reservation.getReservationDate().getDate(),
                "time_slot_id", reservation.getTimeSlot().getId()
        );
        Number key = jdbcInsert.executeAndReturnKey(parameters);
        return reservation.withId(key.longValue());
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
                select reservation.id as id, name, date, time_slot_id, start_at \s
                from reservation left join time_slot \s
                on time_slot_id = time_slot.id
                """;
        return jdbcTemplate.query(sql, ReservationRowMapper.getInstance());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void deleteAll() {
        String sql = "delete from reservation";
        jdbcTemplate.update(sql);
    }
}

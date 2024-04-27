package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class JdbcTemplateReservationRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateReservationRepository(JdbcTemplate jdbcTemplate, DataSource source) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(source)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("reservation_id"),
            resultSet.getString("name"),
            resultSet.getDate("date").toLocalDate(),
            new ReservationTime(
                    resultSet.getLong("time_id"),
                    resultSet.getTime("time_value").toLocalTime())
    );

    @Override
    public Reservation save(Reservation reservationRequest) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", reservationRequest.getName());
        params.put("date", reservationRequest.getDate());
        params.put("time_id", reservationRequest.getTimeId());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return new Reservation(id, reservationRequest);
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                """
                        SELECT
                            r.id AS reservation_id,
                            r.name,
                            r.date,
                            t.id AS time_id,
                            t.start_at AS time_value
                        FROM reservation AS r
                        INNER JOIN reservation_time AS t
                        ON r.time_id = t.id
                        """, reservationRowMapper);
    }

    @Override
    public Optional<Integer> deleteById(Long id) {
        int rowCount = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
        if (rowCount == 0) {
            return Optional.empty();
        }
        return Optional.of(rowCount);
    }
}

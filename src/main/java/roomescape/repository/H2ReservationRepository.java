package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationName;
import roomescape.domain.ReservationTime;

@Primary
@Repository
public class H2ReservationRepository implements ReservationRepository {

    private static final RowMapper<Reservation> ROW_MAPPER = (rs, rowNum) -> {
        final long id = rs.getLong("reservation_id");
        final String name = rs.getString("name");
        final LocalDate date = rs.getDate("date").toLocalDate();
        final long timeId = rs.getLong("time_id");
        final LocalTime time = rs.getTime("time_value").toLocalTime();
        return new Reservation(id, new ReservationName(name), new ReservationDate(date),
                new ReservationTime(timeId, time));
    };

    private final JdbcTemplate jdbcTemplate;

    public H2ReservationRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
                    SELECT
                        r.id as reservation_id,
                        r.name,
                        r.date,
                        t.id as time_id,
                        t.start_at as time_value
                    FROM reservation as r
                    inner join reservation_time as t
                    on r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    @Override
    public Optional<Reservation> findById(final Long id) {
        String sql = """
                    SELECT
                        r.id as reservation_id,
                        r.name,
                        r.date,
                        t.id as time_id,
                        t.start_at as time_value
                    FROM reservation as r
                    inner join reservation_time as t
                    on r.time_id = t.id
                    where r.id = ?
                """;
        try {
            final Reservation reservation = jdbcTemplate.queryForObject(sql, ROW_MAPPER, id);
            return Optional.ofNullable(reservation);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public long add(final Reservation reservation) {
        final Number id = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(
                        Map.of(
                                "name", reservation.getName(),
                                "date", reservation.getDate(),
                                "time_id", reservation.getTime().getId()
                        )
                );
        return id.longValue();
    }

    @Override
    public void deleteById(final Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}

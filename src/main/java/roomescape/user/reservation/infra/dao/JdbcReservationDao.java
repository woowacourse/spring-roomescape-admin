package roomescape.user.reservation.infra.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import roomescape.user.reservation.domain.Reservation;
import roomescape.user.reservation.domain.ReservationRepository;
import roomescape.user.reservation.domain.ReservationTime;

@Repository
public class JdbcReservationDao implements ReservationRepository {

    private final JdbcReservationTimeDao jdbcReservationTimeDao;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            LocalDate.parse(rs.getString("date")),
            new ReservationTime(
                    rs.getLong("time_id"),
                    LocalTime.parse(rs.getString("start_at"))
            )
    );

    public JdbcReservationDao(final JdbcReservationTimeDao jdbcReservationTimeDao, final JdbcTemplate jdbcTemplate) {
        this.jdbcReservationTimeDao = jdbcReservationTimeDao;
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    @Transactional
    public Long save(final Reservation reservation) {
        final Long timeId = findOrCreateReservationTimeId(reservation);

        final Map<String, Object> params = new HashMap<>();
        params.put("name", reservation.getName());
        params.put("date", reservation.getDate().toString());
        params.put("time_id", timeId);

        final Number key = simpleJdbcInsert.executeAndReturnKey(params);

        return key.longValue();
    }

    private Long findOrCreateReservationTimeId(final Reservation reservation) {
        return jdbcReservationTimeDao.findById(reservation.extractTimeId())
                .map(ReservationTime::getId)
                .orElseGet(() -> jdbcReservationTimeDao.save(new ReservationTime(
                        null, reservation.extractTime())));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reservation> findById(final Long id) {
        final String sql = """
                SELECT r.id, r.name, r.date, t.id AS time_id, t.start_at
                FROM reservation r
                INNER JOIN reservation_time t ON r.time_id = t.id
                WHERE r.id = ?
                """;

        return jdbcTemplate.query(sql, rowMapper, id).stream().findFirst();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        final String sql = """
                SELECT r.id, r.name, r.date, t.id AS time_id, t.start_at
                FROM reservation r
                INNER JOIN reservation_time t ON r.time_id = t.id
                """;

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    @Transactional
    public void deleteById(final Long id) {
        final String sql = "DELETE FROM reservation WHERE id = ?";
        final int deletedCount = jdbcTemplate.update(sql, id);

        if (deletedCount == 0) {
            throw new IllegalStateException("Reservation with id " + id + " does not exist");
        }
    }

    @Transactional
    public void clear() {
        jdbcTemplate.update("DELETE FROM reservation");
    }
}

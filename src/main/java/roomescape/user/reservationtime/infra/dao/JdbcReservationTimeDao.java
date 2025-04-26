package roomescape.user.reservationtime.infra.dao;

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
import roomescape.user.reservationtime.domain.ReservationTime;
import roomescape.user.reservationtime.domain.ReservationTimeRepository;

@Repository
public class JdbcReservationTimeDao implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<ReservationTime> rowMapper = (rs, rowNum) -> new ReservationTime(
            rs.getLong("id"),
            LocalTime.parse(rs.getString("start_at"))
    );

    public JdbcReservationTimeDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    @Transactional
    public Long save(final ReservationTime reservationTime) {
        final Map<String, Object> params = new HashMap<>();
        params.put("start_at", reservationTime.getStartAt().toString());

        final Number key = simpleJdbcInsert.executeAndReturnKey(params);
        return key.longValue();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReservationTime> findById(final Long id) {
        final String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.query(sql, rowMapper, id).stream().findFirst();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationTime> findAll() {
        final String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    @Transactional
    public void deleteById(final Long id) {
        final String sql = "DELETE FROM reservation_time WHERE id = ?";
        final int deletedCount = jdbcTemplate.update(sql, id);

        if (deletedCount == 0) {
            throw new IllegalStateException("Reservation time with id " + id + " does not exist");
        }
    }

    @Transactional
    public void clear() {
        jdbcTemplate.update("DELETE FROM reservation_time");
    }
}

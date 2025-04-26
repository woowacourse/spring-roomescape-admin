package roomescape.user.reservation.infra.dao;

import java.time.LocalDate;
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

@Repository
public class JdbcReservationDao implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            LocalDate.parse(rs.getString("date")),
            rs.getLong("time_id")
    );

    public JdbcReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    @Transactional
    public Long save(final Reservation reservation) {
        final Map<String, Object> params = new HashMap<>();
        params.put("name", reservation.getName());
        params.put("date", reservation.getDate().toString());
        params.put("time_id", reservation.getTimeId());

        final Number key = simpleJdbcInsert.executeAndReturnKey(params);
        return key.longValue();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reservation> findById(final Long id) {
        final String sql = "SELECT id, name, date, time_id FROM reservation WHERE id = ?";

        return jdbcTemplate.query(sql, rowMapper, id).stream().findFirst();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        final String sql = "SELECT id, name, date, time_id FROM reservation";

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

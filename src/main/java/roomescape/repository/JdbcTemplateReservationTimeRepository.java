package roomescape.repository;

import java.util.List;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeRepository;
import roomescape.domain.exception.EntityNotFoundException;

@Repository
class JdbcTemplateReservationTimeRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

    public JdbcTemplateReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        Map<String, Object> saveParam = Map.of(
                "start_at", reservationTime.getStartAt()
        );
        Long savedId = simpleInsert
                .executeAndReturnKey(saveParam)
                .longValue();

        return new ReservationTime(savedId, reservationTime.getStartAt());
    }

    @Override
    public void deleteBy(long id) {
        try {
            tryDeleteBy(id);
        } catch (DataIntegrityViolationException exception) {
            throw new IllegalStateException("다른 예약에서 해당 예약 시간을 사용하고 있습니다.");
        }
    }

    private void tryDeleteBy(long id) {
        String sql = "delete from reservation_time where id = ?";

        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "select * from reservation_time";

        return jdbcTemplate.query(sql, getReservationTimeRowMapper());
    }

    @Override
    public ReservationTime findBy(long id) {
        try {
            return tryFindBy(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new EntityNotFoundException("존재하지 않는 예약 시간입니다.");
        }
    }

    private ReservationTime tryFindBy(long id) {
        String sql = "select * from reservation_time where id = ?";

        return jdbcTemplate.queryForObject(sql, getReservationTimeRowMapper(), id);
    }

    private RowMapper<ReservationTime> getReservationTimeRowMapper() {
        return (rs, rowNum) -> new ReservationTime(
                rs.getLong("id"),
                rs.getTime("start_at").toLocalTime()
        );
    }
}

package roomescape.persist.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.persist.entity.ReservationEntity;
import roomescape.persist.entity.ReservationTimeEntity;
import roomescape.domain.Reservation;

@Repository
public class H2ReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public H2ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
                SELECT
                    r.id AS reservation_id,
                    r.name,
                    r.date,
                    t.id AS time_id,
                    t.start_at AS time_value
                FROM reservation r
                INNER JOIN reservation_time t
                    ON r.time_id = t.id""";
        RowMapper<ReservationEntity> rowMapper = (resultSet, rowNum) -> new ReservationEntity(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                new ReservationTimeEntity(
                        resultSet.getLong("time_id"),
                        resultSet.getString("time_value")
                )
        );
        List<ReservationEntity> reservationEntities = jdbcTemplate.query(sql, rowMapper);
        return reservationEntities.stream()
                .map(ReservationEntity::toDomain)
                .toList();
    }

    @Override
    public Reservation add(Reservation reservation) {
        ReservationEntity reservationEntity = ReservationEntity.fromDomain(reservation);
        Map<String, Object> params = new HashMap<>();
        params.put("name", reservationEntity.getName());
        params.put("date", reservationEntity.getDate());
        params.put("time_id", reservationEntity.getTimeEntity().getId());
        long id = jdbcInsert.executeAndReturnKey(params).longValue();
        ReservationEntity savedEntity = reservationEntity.copyWithId(id);
        return savedEntity.toDomain();
    }

    @Override
    public void removeById(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

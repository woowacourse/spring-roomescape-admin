package roomescape.dao;

import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entity.ReservationTimeEntity;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationTimeDaoImpl implements ReservationTimeDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReservationTimeEntity save(ReservationTimeEntity entity) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, entity.startAt().toString());
            return preparedStatement;
        }, keyHolder);
        final long id = keyHolder.getKey().longValue();
        return new ReservationTimeEntity(id, entity.startAt());
    }

    @Override
    public List<ReservationTimeEntity> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Long id = resultSet.getLong("id");
            LocalTime time = resultSet.getObject("start_at", LocalTime.class);
            return new ReservationTimeEntity(id, time);
        });
    }

    @Override
    public boolean deleteById(final Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        final int updated = jdbcTemplate.update(sql, id);
        return updated > 0;
    }

    @Override
    public Optional<ReservationTimeEntity> findById(final Long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        try {
            ReservationTimeEntity timeEntity = jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> {
                LocalTime startAt = resultSet.getObject("start_at", LocalTime.class);
                return new ReservationTimeEntity(id, startAt);
            }, id);
            return Optional.of(timeEntity);
        } catch (IncorrectResultSetColumnCountException e) {
            return Optional.empty();
        }
    }
}

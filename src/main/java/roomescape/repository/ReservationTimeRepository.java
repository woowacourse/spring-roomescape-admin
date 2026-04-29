package roomescape.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.repository.entity.ReservationTimeEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationTimeRepository {

    private static final String FIND_TIME_BY_ID = """
            SELECT id, start_at
            FROM reservation_time
            WHERE id = ?
            """;

    private static final String FIND_ALL_TIME = """
            SELECT id, start_at
            FROM reservation_time
            ORDER BY id
            """;

    private static final String INSERT_TIME = """
            INSERT INTO reservation_time (start_at)
            VALUES (?)
            """;

    private static final String DELETE_TIME_BY_ID = """
            DELETE FROM reservation_time
            WHERE id = ?
            """;

    private final JdbcTemplate jdbcTemplate;


    public List<ReservationTime> findAll() {
        return jdbcTemplate.query(FIND_ALL_TIME, this::mapToEntity)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    public ReservationTime findById(final Long timeId) {
        ReservationTimeEntity entity = jdbcTemplate.queryForObject(
                FIND_TIME_BY_ID,
                this::mapToEntity,
                timeId
        );

        return toDomain(entity);
    }

    public ReservationTime save(final ReservationTime newReservationTime) {
        final long newTimeId = insertReservationTime(newReservationTime);

        return findById(newTimeId);
    }

    public void delete(final Long timeId) {
        jdbcTemplate.update(DELETE_TIME_BY_ID, timeId);
    }


    private long insertReservationTime(final ReservationTime newReservationTime) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_TIME,
                    Statement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setString(1, newReservationTime.getStartAt().toString());

            return preparedStatement;
        }, keyHolder);

        return generatedIdFrom(keyHolder);
    }

    private long generatedIdFrom(final KeyHolder keyHolder) {
        if (keyHolder.getKey() == null) {
            throw new IllegalStateException("생성된 id를 가져오지 못했습니다.");
        }

        return keyHolder.getKey().longValue();
    }


    private ReservationTimeEntity mapToEntity(final ResultSet resultSet, final int rowNum) throws SQLException {
        return new ReservationTimeEntity(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        );
    }

    private ReservationTime toDomain(final ReservationTimeEntity entity) {
        return ReservationTime.restore(
                entity.id(),
                entity.startAt()
        );
    }
}

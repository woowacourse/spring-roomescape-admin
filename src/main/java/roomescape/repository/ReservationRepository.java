package roomescape.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.entity.ReservationTimeEntity;
import roomescape.repository.entity.ReservationWithTimeEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private static final String FIND_RESERVATION_BY_ID = """
            SELECT
            r.id AS reservation_id,
            r.name AS reservation_name,
            r.date AS reservation_date,
            t.id AS time_id,
            t.start_at AS time_start_at
            FROM reservation r
            JOIN reservation_time t ON r.time_id = t.id
            WHERE r.id = ?
            """;

    private static final String FIND_ALL_RESERVATION = """
            SELECT
                r.id AS reservation_id,
                r.name AS reservation_name,
                r.date AS reservation_date,
                t.id AS time_id,
                t.start_at AS time_start_at
            FROM reservation r
            JOIN reservation_time t ON r.time_id = t.id
            ORDER BY r.id
            """;

    private static final String INSERT_RESERVATION = """
            INSERT INTO reservation (name, date, time_id)
            VALUES (?, ?, ?)
            """;

    private static final String DELETE_RESERVATION_BY_ID = """
            DELETE FROM reservation
            WHERE id = ?
            """;

    private final JdbcTemplate jdbcTemplate;


    public List<Reservation> findAll() {
        return jdbcTemplate.query(FIND_ALL_RESERVATION, this::mapToEntity)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    public Reservation findById(final Long reservationId) {
        final ReservationWithTimeEntity reservationWithTimeEntity = jdbcTemplate.queryForObject(
                FIND_RESERVATION_BY_ID,
                this::mapToEntity,
                reservationId
        );

        return toDomain(reservationWithTimeEntity);
    }

    public Reservation save(final Reservation newReservation) {
        final long newReservationId = insertReservation(newReservation);

        return findById(newReservationId);
    }

    public void deleteById(final Long reservationId) {
        jdbcTemplate.update(DELETE_RESERVATION_BY_ID, reservationId);
    }


    private long insertReservation(final Reservation newReservation) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_RESERVATION,
                    Statement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setString(1, newReservation.getName());
            preparedStatement.setString(2, newReservation.getDate().toString());
            preparedStatement.setLong(3, newReservation.getTime().getId());

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


    private ReservationWithTimeEntity mapToEntity(
            final ResultSet resultSet,
            final int rowNum
    ) throws SQLException {
        return new ReservationWithTimeEntity(
                resultSet.getLong("reservation_id"),
                resultSet.getString("reservation_name"),
                resultSet.getDate("reservation_date").toLocalDate(),
                new ReservationTimeEntity(
                        resultSet.getLong("time_id"),
                        resultSet.getTime("time_start_at").toLocalTime()
                )
        );
    }

    private Reservation toDomain(final ReservationWithTimeEntity entity) {
        ReservationTime time = ReservationTime.restore(
                entity.time().id(),
                entity.time().startAt()
        );

        return Reservation.restore(
                entity.id(),
                entity.name(),
                entity.date(),
                time
        );
    }
}

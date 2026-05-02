package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                    new String[]{"id"}
            );

            statement.setString(1, reservation.name().value());
            statement.setString(2, reservation.date().value().toString());
            statement.setLong(3, reservation.time().id());
            return statement;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("[ERROR] 예약 ID가 생성되지 않았습니다.");
        }

        return new Reservation(
                key.longValue(),
                reservation.name(),
                reservation.date(),
                reservation.time()
        );
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query("""
                        SELECT r.id AS reservation_id,
                               r.name,
                               r.date,
                               t.id AS time_id,
                               t.start_at
                        FROM reservation r
                        INNER JOIN reservation_time t
                            ON r.time_id = t.id
                        ORDER BY r.id
                        """,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("reservation_id"),
                        new Name(resultSet.getString("name")),
                        ReservationDate.from(resultSet.getString("date")),
                        ReservationTime.from(
                                resultSet.getLong("time_id"),
                                resultSet.getString("start_at")
                        )
                )
        );
    }

    public boolean hasReservationAt(ReservationDate date, ReservationTime time) {
        Long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM reservation WHERE date = ? AND time_id = ?",
                Long.class,
                date.value().toString(),
                time.id()
        );

        return count != null && count > 0;
    }

    public void deleteById(Long id) {
        int deletedCount = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);

        if (deletedCount == 0) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 예약입니다.");
        }
    }
}

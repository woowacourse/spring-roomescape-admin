package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.exception.ErrorCode;
import roomescape.exception.PersistenceException;
import roomescape.exception.ReservationException;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class JdbcReservationRepository implements ReservationRepository {
    private static final String FIND_ALL_SQL = """
            SELECT
                r.id AS reservation_id,
                r.name,
                r.date,
                t.id AS time_id,
                t.start_at
            FROM reservation r
            INNER JOIN reservation_time t
                ON r.time_id = t.id
            """;

    private static final String INSERT_SQL = """
            INSERT INTO reservation (name, date, time_id)
            VALUES (?, ?, ?)
            """;

    private static final String DELETE_SQL = """
            DELETE FROM reservation
            WHERE id = ?
            """;

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        ReservationTime reservationTime = new ReservationTime(
                resultSet.getLong("time_id"),
                resultSet.getString("start_at")
        );

        return new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                reservationTime
        );
    };

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, reservationRowMapper);
    }

    @Override
    public Reservation save(String name, String date, ReservationTime time) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowCount = insert(name, date, time, keyHolder);
        validateCreatedRowCount(rowCount);

        Long id = getGeneratedId(keyHolder);
        return new Reservation(id, name, date, time);
    }

    @Override
    public void deleteById(Long id) {
        int deletedRowCount = jdbcTemplate.update(DELETE_SQL, id);

        if (deletedRowCount == 0) {
            throw new ReservationException(ErrorCode.RESERVATION_NOT_FOUND);
        }
    }

    private int insert(String name, String date, ReservationTime time, KeyHolder keyHolder) {
        return jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_SQL,
                    new String[]{"id"}
            );
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, date);
            preparedStatement.setLong(3, time.getId());
            return preparedStatement;
        }, keyHolder);
    }

    private void validateCreatedRowCount(int rowCount) {
        if (rowCount != 1) {
            throw new PersistenceException(ErrorCode.RESERVATION_CREATE_FAILED);
        }
    }

    private Long getGeneratedId(KeyHolder keyHolder) {
        Number key = keyHolder.getKey();
        if (key == null) {
            throw new PersistenceException(ErrorCode.RESERVATION_CREATE_FAILED);
        }
        return key.longValue();
    }
}

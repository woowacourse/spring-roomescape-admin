package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.exception.ErrorCode;
import roomescape.exception.PersistenceException;
import roomescape.exception.ReservationException;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class JdbcReservationDao implements ReservationDao {
    private static final String FIND_ALL_SQL = """
            SELECT *
            FROM reservation
            """;

    private static final String INSERT_SQL = """
            INSERT INTO reservation (name, date, time)
            VALUES (?, ?, ?)
            """;

    private static final String DELETE_SQL = """
            DELETE FROM reservation
            WHERE id = ?
            """;

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) ->
            new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    resultSet.getString("time")
            );

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, reservationRowMapper);
    }

    @Override
    public Reservation create(String name, String date, String time) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowCount = insert(name, date, time, keyHolder);
        validateCreatedRowCount(rowCount);

        Long id = getGeneratedId(keyHolder);
        return new Reservation(id, name, date, time);
    }

    @Override
    public void delete(Long id) {
        int deletedRowCount = jdbcTemplate.update(DELETE_SQL, id);

        if (deletedRowCount == 0) {
            throw new ReservationException(ErrorCode.RESERVATION_NOT_FOUND);
        }
    }

    private int insert(String name, String date, String time, KeyHolder keyHolder) {
        return jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_SQL,
                    new String[]{"id"}
            );
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, time);
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

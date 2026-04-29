package roomescape.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.exception.ErrorCode;
import roomescape.exception.PersistenceException;
import roomescape.exception.ReservationException;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class JdbcReservationTimeDao implements ReservationTimeDao {
    private static final String FIND_ALL_SQL = """
            SELECT id, start_at
            FROM reservation_time
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT id, start_at
            FROM reservation_time
            WHERE id = ?
            """;

    private static final String INSERT_SQL = """
            INSERT INTO reservation_time (start_at)
            VALUES (?)
            """;

    private static final String DELETE_SQL = """
            DELETE FROM reservation_time
            WHERE id = ?
            """;

    private final RowMapper<ReservationTime> reservationTimeRowMapper = (resultSet, rowNum) ->
            new ReservationTime(
                    resultSet.getLong("id"),
                    resultSet.getString("start_at")
            );

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReservationTime create(String startAt) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowCount = insert(startAt, keyHolder);
        validateCreatedRowCount(rowCount);

        Long id = getGeneratedId(keyHolder);
        return new ReservationTime(id, startAt);
    }

    @Override
    public List<ReservationTime> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, reservationTimeRowMapper);
    }

    @Override
    public void delete(Long id) {
        int deletedRowCount = jdbcTemplate.update(DELETE_SQL, id);

        if (deletedRowCount == 0) {
            throw new ReservationException(ErrorCode.RESERVATION_TIME_NOT_FOUND);
        }
    }

    @Override
    public ReservationTime findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID_SQL, reservationTimeRowMapper, id);
        } catch (EmptyResultDataAccessException exception) {
            throw new ReservationException(ErrorCode.RESERVATION_TIME_NOT_FOUND);
        }
    }

    private int insert(String startAt, KeyHolder keyHolder) {
        return jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_SQL,
                    new String[]{"id"}
            );
            preparedStatement.setString(1, startAt);
            return preparedStatement;
        }, keyHolder);
    }

    private void validateCreatedRowCount(int rowCount) {
        if (rowCount != 1) {
            throw new PersistenceException(ErrorCode.RESERVATION_TIME_CREATE_FAILED);
        }
    }

    private Long getGeneratedId(KeyHolder keyHolder) {
        Number key = keyHolder.getKey();
        if (key == null) {
            throw new PersistenceException(ErrorCode.RESERVATION_TIME_CREATE_FAILED);
        }
        return key.longValue();
    }
}


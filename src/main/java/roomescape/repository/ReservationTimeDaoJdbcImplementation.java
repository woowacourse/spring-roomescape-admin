package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeDaoJdbcImplementation implements ReservationTimeDao {

    private static final String TEST_TIME = "20:00";

    private static final String INSERT_RESERVATION_TIME_QUERY = "INSERT INTO reservation_time(start_at) VALUES (?)";
    private static final String SELECT_ALL_RESERVATION_TIME_QUERY = "SELECT id, start_at FROM reservation_time;";
    private static final String SELECT_SPECIFIC_RESERVATION_TIME_QUERY = "SELECT id, start_at FROM reservation_time WHERE id = ?;";
    private static final String DELETE_SPECIFIC_RESERVATION_TIME_QUERY = "DELETE FROM reservation_time WHERE id = ?;";

    private final JdbcTemplate jdbcTemplate;
    private final ReservationTimeEntityMapper mapper;

    public ReservationTimeDaoJdbcImplementation(JdbcTemplate jdbcTemplate, ReservationTimeEntityMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }


    @Override
    public ReservationTime save(ReservationTime time) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        ReservationTime reservationTime = ReservationTime.constructWithoutId(TEST_TIME);
        ReservationTimeEntity entity = mapper.toReservationTimeEntity(reservationTime);

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_RESERVATION_TIME_QUERY,
                            Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, entity.startAt());
                    return ps;
                },
                keyHolder
        );

        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return mapper.toReservationTime(entity.initializeWithId(id));
    }

    @Override
    public ReservationTime findById(Long targetId) {
        try {
            return jdbcTemplate.queryForObject(
                    SELECT_SPECIFIC_RESERVATION_TIME_QUERY,
                    (resultSet, rowNum) -> {
                        ReservationTimeEntity entity = new ReservationTimeEntity(
                                resultSet.getLong("id"),
                                resultSet.getString("start_at")
                        );

                        return mapper.toReservationTime(entity);
                    },
                    targetId
            );
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("찾는 대상이 존재하지 않습니다.");
        }
    }

    @Override
    public List<ReservationTime> findAll() {
        return jdbcTemplate.query(
                SELECT_ALL_RESERVATION_TIME_QUERY,
                (resultSet, rowNum) -> {
                    ReservationTimeEntity entity = new ReservationTimeEntity(
                            resultSet.getLong("id"),
                            resultSet.getString("start_at")
                    );

                    return mapper.toReservationTime(entity);
                }
        );
    }

    @Override
    public void delete(Long targetId) {
        int rows = jdbcTemplate.update(DELETE_SPECIFIC_RESERVATION_TIME_QUERY, targetId);
        if (rows == 0) {
            throw new IllegalArgumentException("삭제 대상이 존재하지 않습니다");
        }
    }
}

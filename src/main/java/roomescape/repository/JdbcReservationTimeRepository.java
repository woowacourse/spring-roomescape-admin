package roomescape.repository;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class JdbcReservationTimeRepository implements ReservationTimeRepository {

    private static final String INSERT_SQL = "INSERT INTO reservation_time(start_at) VALUES(?)";
    private static final String SELECT_SQL = "SELECT * FROM reservation_time";
    private static final String DELETE_SQL = "DELETE FROM reservation_time";
    private static final String WHERE_ID = " WHERE = ?";

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ReservationTime> rowMapper = (resultSet, rowNum) -> {
        ReservationTime reservationTime = new ReservationTime(
                resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("start_at"))
        );
        return reservationTime;
    };

    @Override
    public Long save(ReservationTime reservationTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
            preparedStatement.setString(1, reservationTime.getStartAt().toString());
            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public List<ReservationTime> findAll() {
        return jdbcTemplate.query(SELECT_SQL, rowMapper);
    }

    @Override
    public ReservationTime findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_SQL + WHERE_ID, rowMapper, id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_SQL + WHERE_ID, id);
    }
}

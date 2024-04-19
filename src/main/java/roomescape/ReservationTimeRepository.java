package roomescape;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationTimeRepository {

    private static final String TABLE_NAME = "reservation_time";
    private static final RowMapper<ReservationTime> ROW_MAPPER = (resultSet, rowNum) -> new ReservationTime(
            resultSet.getLong("id"), resultSet.getString("start_at"));

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("SELECT id, start_at FROM %s".formatted(TABLE_NAME), ROW_MAPPER);
    }

    public ReservationTime create(ReservationTime reservationTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> insertQuery(connection, reservationTime), keyHolder);

        Long id = keyHolder.getKey().longValue();
        return reservationTime.toEntity(id);
    }

    private PreparedStatement insertQuery(Connection connection, ReservationTime reservationTime) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO %s (start_at) VALUES (?)".formatted(TABLE_NAME), new String[]{"id"});
        preparedStatement.setString(1, reservationTime.startAt());
        return preparedStatement;
    }

}

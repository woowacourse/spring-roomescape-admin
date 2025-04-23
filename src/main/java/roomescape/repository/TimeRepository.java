package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import roomescape.model.Time;

@org.springframework.stereotype.Repository
public class TimeRepository extends Repository<Time> {

    public TimeRepository(Dao dao) {
        super(dao);
    }

    @Override
    protected String getAllQuery() {
        return "SELECT id, start_at FROM reservation_time";
    }

    @Override
    protected String saveQuery() {
        return "INSERT INTO reservation_time (start_at) VALUES (?)";
    }

    @Override
    protected String removeQuery() {
        return "DELETE FROM reservation_time WHERE id = ?";
    }

    @Override
    protected Time rowMapper(ResultSet resultSet, int rowNum) throws SQLException {
        return new Time(
            resultSet.getLong("id"),
            resultSet.getObject("start_at", LocalTime.class)
        );
    }

    @Override
    protected PreparedStatement preparedStatementProvider(PreparedStatement preparedStatement, Time object) throws
        SQLException {
        preparedStatement.setObject(1, object.startAt());
        return preparedStatement;
    }
}

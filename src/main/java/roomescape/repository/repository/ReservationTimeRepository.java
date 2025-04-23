package roomescape.repository.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Optional;

import roomescape.model.ReservationTime;
import roomescape.repository.Dao;

@org.springframework.stereotype.Repository
public class ReservationTimeRepository extends Repository<ReservationTime> {

    public ReservationTimeRepository(Dao dao) {
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
    protected ReservationTime rowMapper(ResultSet resultSet, int rowNum) throws SQLException {
        return new ReservationTime(
            resultSet.getLong("id"),
            resultSet.getObject("start_at", LocalTime.class)
        );
    }

    @Override
    protected PreparedStatement preparedStatementProvider(PreparedStatement preparedStatement,
        ReservationTime object) throws SQLException {
        preparedStatement.setObject(1, object.startAt());
        return preparedStatement;
    }

    public Optional<ReservationTime> findById(Long id) {
        String query = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        return dao.findById(query, id, this::rowMapper);
    }
}

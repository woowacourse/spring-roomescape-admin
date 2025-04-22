package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import roomescape.model.Reservation;

@org.springframework.stereotype.Repository
public class ReservationRepository extends Repository<Reservation> {

    public ReservationRepository(Dao dao) {
        super(dao);
    }

    @Override
    protected String getAllQuery() {
        return "SELECT id, name, date, time FROM reservation";
    }

    @Override
    protected String saveQuery() {
        return "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
    }

    @Override
    protected String removeQuery() {
        return "DELETE FROM reservation WHERE id = ?";
    }

    @Override
    protected Reservation rowMapper(ResultSet resultSet, int rowNum) throws SQLException {
        return new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getObject("date", LocalDate.class),
            resultSet.getObject("time", LocalTime.class)
        );
    }

    @Override
    protected PreparedStatement preparedStatementProvider(PreparedStatement preparedStatement, Reservation reservation)
        throws SQLException {
        preparedStatement.setString(1, reservation.name());
        preparedStatement.setObject(2, reservation.date());
        preparedStatement.setObject(3, reservation.time());
        return preparedStatement;
    }
}

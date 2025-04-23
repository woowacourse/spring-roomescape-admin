package roomescape.repository.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.Dao;

@org.springframework.stereotype.Repository
public class ReservationRepository extends Repository<Reservation> {

    public ReservationRepository(Dao dao) {
        super(dao);
    }

    @Override
    protected String getAllQuery() {
        return """
            SELECT
                r.id as reservation_id,
                r.name,
                r.date,
                t.id as time_id,
                t.start_at as time_value
            FROM reservation as r
            inner join reservation_time as t
            on r.time_id = t.id
            """;
    }

    @Override
    protected String saveQuery() {
        return "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
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
            new ReservationTime(
                resultSet.getLong("time_id"),
                resultSet.getObject("time_value", LocalTime.class)
            )
        );
    }

    @Override
    protected PreparedStatement preparedStatementParameterSetup(PreparedStatement preparedStatement, Reservation object)
        throws SQLException {
        preparedStatement.setString(1, object.name());
        preparedStatement.setObject(2, object.date());
        preparedStatement.setObject(3, object.reservationTime().id());
        return preparedStatement;
    }
}

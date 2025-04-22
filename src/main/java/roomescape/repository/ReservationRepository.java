package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Component;

import roomescape.model.Reservation;

@Component
public class ReservationRepository {

    private final Dao dao;

    public ReservationRepository(Dao dao) {
        this.dao = dao;
    }

    public List<Reservation> getAll() {
        String query = "SELECT id, name, date, time FROM reservation";
        return dao.getAll(query, this::reservationRowMapper);
    }

    private Reservation reservationRowMapper(ResultSet resultSet, int rowNum) throws SQLException {
        return new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getObject("date", LocalDate.class),
            resultSet.getObject("time", LocalTime.class)
        );
    }

    public Reservation save(Reservation reservation) {
        String query = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        return dao.save(query, reservation, (PreparedStatementProvider<Reservation>)this::psProvider);
    }

    private PreparedStatement psProvider(PreparedStatement preparedStatement, Reservation reservation)
        throws SQLException {
        preparedStatement.setString(1, reservation.name());
        preparedStatement.setObject(2, reservation.date());
        preparedStatement.setObject(3, reservation.time());
        return preparedStatement;
    }

    public void remove(Long id) {
        String query = "DELETE FROM reservation WHERE id = ?";
        dao.remove(query, id);
    }
}

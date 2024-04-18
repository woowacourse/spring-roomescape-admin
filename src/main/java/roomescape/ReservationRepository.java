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
public class ReservationRepository {

    private static final String TABLE_NAME = "reservation";
    private static final RowMapper<Reservation> ROW_MAPPER = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("date"),
            resultSet.getString("time"));

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT id, name, date, time FROM %s".formatted(TABLE_NAME), ROW_MAPPER);
    }

    public Reservation create(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> insertQuery(connection, reservation), keyHolder);

        Long id = keyHolder.getKey().longValue();
        return reservation.toEntity(id);
    }

    private PreparedStatement insertQuery(Connection connection, Reservation reservation) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO %s (name, date, time) VALUES (?, ?, ?)".formatted(TABLE_NAME), new String[]{"id"});
        preparedStatement.setString(1, reservation.name());
        preparedStatement.setString(2, reservation.date());
        preparedStatement.setString(3, reservation.time());
        return preparedStatement;
    }

    public void deleteById(Long id) {
        Reservation foundReservation = findById(id);
        jdbcTemplate.update("DELETE FROM %s WHERE id = ?".formatted(TABLE_NAME), foundReservation.id());
    }

    private Reservation findById(Long id) {
        Reservation reservation = jdbcTemplate.queryForObject(
                "SELECT id, name, date, time FROM %s WHERE id = ?".formatted(TABLE_NAME), ROW_MAPPER, id);

        if (reservation == null) {
            throw new IllegalStateException("해당 예약이 없습니다.");
        }
        return reservation;
    }

}

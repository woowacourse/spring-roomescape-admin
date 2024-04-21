package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationJDBCRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT * FROM Reservation";
        List<Reservation> reservations = jdbcTemplate.query(sql,
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            resultSet.getString("time")
                    );
                    return reservation;
                });
        return reservations;
    }

    @Override
    public Reservation save(final Reservation reservation) {
        String sql = "INSERT INTO Reservation (name, date, time) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"id"});
            statement.setString(1, reservation.getName());
            statement.setString(2, reservation.getDate());
            statement.setString(3, reservation.getTime());
            return statement;
        }, keyHolder);
        long id = keyHolder.getKey().longValue();
        return new Reservation(id, reservation);
    }

    @Override
    public boolean existsById(final long id) {
        String sql = "SELECT EXISTS(SELECT 1 FROM Reservation WHERE id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, id);
    }

    @Override
    public void deleteById(final long id) {
        String sql = "DELETE FROM Reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

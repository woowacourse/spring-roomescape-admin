package roomescape.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationRepository {

    private static final RowMapper<Reservation> reservationRowMapper;

    @Autowired
    private final JdbcTemplate template;

    static {
        reservationRowMapper = (resultSet, resultNumber) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime());
    }

    public ReservationRepository(JdbcTemplate template) {
        this.template = template;
    }

    public List<Reservation> findAll() {
        return template.query(
                "SELECT * FROM reservation",
                (resultSet, resultNumber) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("time").toLocalTime())
        );
    }

    public Optional<Reservation> findById(long id) {
        String sql = "SELECT * FROM reservation WHERE ?";
        try {
            Reservation reservation = template.queryForObject(sql, reservationRowMapper, id);
            return Optional.of(reservation);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public long add(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) values (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(
                (connection) -> {
                    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, reservation.getName());
                    statement.setDate(2, Date.valueOf(reservation.getDate()));
                    statement.setTime(3, Time.valueOf(reservation.getTime()));
                    return statement;
                },
                keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM reservation WHERE reservation.id = ?";
        template.update(sql, id);
    }
}

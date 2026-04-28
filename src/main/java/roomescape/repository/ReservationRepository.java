package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation create(Reservation reservation) {
        String createSql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(createSql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, reservation.name());
            statement.setObject(2, reservation.date());
            statement.setObject(3, reservation.time());

            return statement;
        }, keyHolder);

        return reservation.identify(keyHolder.getKey().longValue());
    }

    public List<Reservation> findAll() {
        String findSql = "SELECT * FROM reservation";

        return jdbcTemplate.query(findSql, reservationRowMapper());
    }

    public void delete(long id) {
        String deleteSql = "DELETE FROM reservation WHERE id = ?";

        jdbcTemplate.update(deleteSql, id);
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getObject("date", LocalDate.class),
                resultSet.getObject("time", LocalTime.class)
        );
    }
}

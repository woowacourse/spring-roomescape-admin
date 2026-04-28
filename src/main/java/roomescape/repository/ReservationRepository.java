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
import roomescape.dto.ReservationCreateRequest;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation create(ReservationCreateRequest createRequest) {
        String createSql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(createSql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, createRequest.name());
            statement.setObject(2, createRequest.date());
            statement.setObject(3, createRequest.time());

            return statement;
        }, keyHolder);

        return new Reservation(
                keyHolder.getKey().longValue(),
                createRequest.name(),
                createRequest.date(),
                createRequest.time()
        );
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

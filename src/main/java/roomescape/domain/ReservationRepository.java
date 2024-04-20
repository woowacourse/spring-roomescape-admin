package roomescape.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
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
            resultSet.getString("date"),
            resultSet.getString("name"),
            new ReservationTime(resultSet.getLong("time_id"), resultSet.getString("start_at")));

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Reservation> findById(Long id) {
        Reservation reservation = jdbcTemplate.queryForObject("""
                SELECT
                    r.id AS id,
                    name,
                    date,
                    t.id AS time_id,
                    start_at
                FROM reservation AS r
                INNER JOIN reservation_time AS t
                on r.time_id = t.id
                WHERE r.id = ?""".formatted(TABLE_NAME), ROW_MAPPER, id);

        return Optional.ofNullable(reservation);
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query("""
                SELECT
                    r.id AS id,
                    name,
                    date,
                    t.id AS time_id,
                    start_at
                FROM reservation AS r
                INNER JOIN reservation_time AS t
                on r.time_id = t.id""".formatted(TABLE_NAME), ROW_MAPPER);
    }

    public Reservation create(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> insertQuery(connection, reservation), keyHolder);

        Long id = keyHolder.getKey().longValue();
        return findById(id).orElseThrow(IllegalStateException::new);
    }

    private PreparedStatement insertQuery(Connection connection, Reservation reservation) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO %s (name, date, time_id) VALUES (?, ?, ?)".formatted(TABLE_NAME), new String[]{"id"});
        preparedStatement.setString(1, reservation.name());
        preparedStatement.setString(2, reservation.date());
        preparedStatement.setLong(3, reservation.time().id());
        return preparedStatement;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM %s WHERE id = ?".formatted(TABLE_NAME), id);
    }

}

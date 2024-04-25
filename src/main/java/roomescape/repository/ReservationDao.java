package roomescape.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation2;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Reservation2 save(final Reservation2 reservation, final long timeId) {
        final String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setDate(2, Date.valueOf(reservation.getDate()));
            preparedStatement.setLong(3, timeId);
            return preparedStatement;
        }, keyHolder);

        final long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return reservation.toReservation(id);
    }

    public Optional<Reservation2> findById(final long id) {
        final String sql = """
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                FROM reservation as r
                inner join reservation_time as t
                on r.time_id = t.id
                where r.id = ?
                """;

        try {
            final Reservation2 reservation = jdbcTemplate.queryForObject(
                    sql, (resultSet, rowNum) -> new Reservation2(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            resultSet.getLong("time_id"),
                            resultSet.getString("time_value")
                    ), id);
            return Optional.ofNullable(reservation);
        } catch (final EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public List<Reservation2> findAll() {
        final String sql = """
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                FROM reservation as r
                inner join reservation_time as t
                on r.time_id = t.id""";

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Reservation2(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getLong("time_id"),
                        resultSet.getString("time_value")
                ));
    }

    public void remove(final long id) {
        final String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

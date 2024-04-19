package roomescape.repository;

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
import roomescape.dto.ReservationRequest;
import roomescape.model.Reservation;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public long save(final ReservationRequest reservation) {
        final String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement = connection.prepareStatement(
                    sql, new String[]{"id"});
            preparedStatement.setString(1, reservation.name());
            preparedStatement.setString(2, reservation.date());
            preparedStatement.setString(3, reservation.time());
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Optional<Reservation> findById(final Long id) {
        try {
            final Reservation reservation = jdbcTemplate.queryForObject(
                    "SELECT id, name, date, time FROM reservation WHERE id = ?",
                    (resultSet, rowNum) -> new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            resultSet.getString("time")
                    ), id);
            return Optional.ofNullable(reservation);
        } catch (final EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name, date, time FROM reservation",
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("time")
                ));
    }

    public void remove(final long id) {
        final String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

package roomescape.reservation;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(final ReservationRequest reservationRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation(name, date, time) values (?, ? ,?)", new String[]{"id"});
            ps.setString(1, reservationRequest.getName());
            ps.setString(2, reservationRequest.getDate());
            ps.setString(3, reservationRequest.getTime());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Optional<Reservation> findById(final Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from reservation where id = ?",
                    (resultSet, rowNum) -> {
                        Reservation reservation = new Reservation(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getString("date"),
                                resultSet.getString("time")
                        );
                        return reservation;
                    }, id));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query("select * from reservation",
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            resultSet.getString("time")
                    );
                    return reservation;
                });
    }

    public void deleteById(final Long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}

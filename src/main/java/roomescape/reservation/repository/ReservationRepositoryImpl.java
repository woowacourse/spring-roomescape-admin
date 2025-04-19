package roomescape.reservation.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.exception.EntityNotFoundException;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select * from reservation";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getTimestamp("date_time").toLocalDateTime()
        ));
    }

    @Override
    public Optional<Reservation> findById(long id) {
        String sql = "select * from reservation where id = ?";

        try {
            Reservation reservation = jdbcTemplate.queryForObject(sql, (resultSet, rowNum) ->
                    new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getTimestamp("date_time").toLocalDateTime()
                    ), id);

            return Optional.ofNullable(reservation);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Reservation save(Reservation reservation) {
        if (reservation.existId()) {
            return update(reservation);
        }

        return create(reservation);
    }

    public Reservation create(Reservation reservation) {
        String sql = "insert into reservation (name, date_time) values (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(conn -> {
                    PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, reservation.getName());
                    ps.setString(2, reservation.getDateTime().toString());
                    return ps;
                }
                , keyHolder);

        long id = keyHolder.getKey().longValue();

        return new Reservation(id, reservation.getName(), reservation.getDateTime());
    }

    public Reservation update(Reservation reservation) {
        String sql = "update reservation set name = ?, date_time = ? where id = ?";

        int update = jdbcTemplate.update(sql, reservation.getName(), reservation.getDateTime(), reservation.getId());

        if (update == 0) {
            throw new EntityNotFoundException("Reservation with id " + reservation.getId() + " not found");
        }

        return reservation;
    }

    @Override
    public void deleteById(long id) {
        String sql = "delete from reservation where id = ?";

        int result = jdbcTemplate.update(sql, id);

        if (result != 1) {
            throw new EntityNotFoundException("Reservation with id " + id + " not found");
        }
    }
}

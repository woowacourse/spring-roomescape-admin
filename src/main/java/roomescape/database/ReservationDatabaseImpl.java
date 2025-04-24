package roomescape.database;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;

import java.util.List;

@Repository
public class ReservationDatabaseImpl implements ReservationDatabase {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDatabaseImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "select id, name, date, time from reservation",
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getDate("date").toLocalDate(),
                            resultSet.getTime("time").toLocalTime()
                    );
                    return reservation;
                });
    }

    @Override
    public Reservation findById(Long id) {
        String sql = "select id, name, date, time from reservation where id = ?";

        return jdbcTemplate.queryForObject(sql,
                (resultSet, rowNum) ->
                        new Reservation(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getDate("date").toLocalDate(),
                                resultSet.getTime("time").toLocalTime()
                        ), id);

    }

    @Override
    public Reservation add(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, reservation.getName(), reservation.getDate(), reservation.getTime());
        return reservation;
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, Long.valueOf(id));
    }
}

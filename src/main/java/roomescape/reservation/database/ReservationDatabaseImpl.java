package roomescape.reservation.database;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;

import java.sql.PreparedStatement;
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
        Long id = insertWithKeyHolder(reservation);
        return findById(id);
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, Long.valueOf(id));
    }

    private Long insertWithKeyHolder(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setString(3, reservation.getTime().toString());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
}

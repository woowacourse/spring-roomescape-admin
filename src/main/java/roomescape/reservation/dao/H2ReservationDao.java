package roomescape.reservation.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.common.Dao;
import roomescape.reservation.Reservation;
import roomescape.reservationTime.ReservationTime;

@Component
public class H2ReservationDao implements Dao<Reservation> {

    private final JdbcTemplate jdbcTemplate;

    public H2ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation add(Reservation reservation) {
        String sql = "insert into reservation(name, date, time_id) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setString(2, reservation.getDate().toString());
            preparedStatement.setLong(3, reservation.getTime().getId());
            return preparedStatement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Override
    public Reservation getById(Long id) {
        String sql = "select id, name, date, time_id from reservation_time where id = ?";
        return jdbcTemplate.queryForObject(sql,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("date").toLocalDate(),
                        new ReservationTime(
                                resultSet.getLong("time_id")
                        )
                ));
    }

    @Override
    public List<Reservation> getAll() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, "
                + "t.id as time_id, "
                + "t.start_at as time_value "
                + "FROM reservation as r "
                + "inner join reservation_time as t "
                + "on r.time_id = t.id";

        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("reservation_id"),
                        resultSet.getString("name"),
                        resultSet.getDate("date").toLocalDate(),
                        new ReservationTime(
                                resultSet.getLong("time_id"),
                                resultSet.getTime("start_at").toLocalTime()
                        )
                )
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}

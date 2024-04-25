package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ReservationDAO {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDAO(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation insert(final Reservation reservation) {
        final String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        final String name = reservation.getName();
        final LocalDate date = reservation.getDate();
        final ReservationTime time = reservation.getTime();

        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, name);
            preparedStatement.setDate(2, Date.valueOf(date));
            preparedStatement.setLong(3, time.getId());
            return preparedStatement;
        }, keyHolder);

        final long id = keyHolder.getKey().longValue();
        return new Reservation(id, name, date, time);
    }

    public List<Reservation> selectAll() {
        final String sql =
                "SELECT " +
                        "r.id AS reservation_id, " +
                        "r.name, " +
                        "r.date, " +
                        "t.id AS time_id, " +
                        "t.start_at AS time_value " +
                "FROM reservation AS r " +
                "INNER JOIN reservation_time AS t " +
                "ON r.time_id = t.id";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        resultSet.getTime("time_value").toLocalTime()
                )
        ));
    }
}

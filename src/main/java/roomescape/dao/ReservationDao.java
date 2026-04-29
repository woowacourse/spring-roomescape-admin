package roomescape.dao;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.ReservationReq;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAllReservations() {
        String sql = "SELECT\n"
                + "    r.id as reservation_id,\n"
                + "    r.name,\n"
                + "    r.date,\n"
                + "    t.id as time_id,\n"
                + "    t.start_at as time_value\n"
                + "FROM reservation as r\n"
                + "INNER JOIN reservation_time as t\n"
                + "  ON r.time_id = t.id";
        List<Reservation> reservations = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("reservation_id"),
                            resultSet.getString("name"),
                            resultSet.getDate("date").toLocalDate(),
                            new ReservationTime(
                                    resultSet.getLong("time_id"),
                                    resultSet.getTime("time_value").toLocalTime()
                            )
                    );
                    return reservation;
                });
        return reservations;
    }

    public Reservation findReservationById(Long id) {
        String sql = "SELECT\n"
                + "    r.id as reservation_id,\n"
                + "    r.name,\n"
                + "    r.date,\n"
                + "    t.id as time_id,\n"
                + "    t.start_at as time_value\n"
                + "FROM reservation as r\n"
                + "INNER JOIN reservation_time as t\n"
                + "  ON r.time_id = t.id\n"
                + "WHERE r.id = ?";
        Reservation reservation = jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> {
                    Reservation newReservation = new Reservation(
                            resultSet.getLong("reservation_id"),
                            resultSet.getString("name"),
                            LocalDate.parse(resultSet.getString("date")),
                            new ReservationTime(
                                    resultSet.getLong("time_id"),
                                    LocalTime.parse(resultSet.getString("start_at"))
                            )
                    );
                    return newReservation;
                }, id);
        return reservation;
    }

    public Long insertWithKeyHolder(ReservationReq reservationReq) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"}
            );
            ps.setString(1, reservationReq.name());
            ps.setString(2, reservationReq.date().toString());
            ps.setLong(3, reservationReq.timeId());
            return ps;
        }, keyHolder);
        Long id = keyHolder.getKey().longValue();

        return id;
    }

    public int delete(Long id) {
        return jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}

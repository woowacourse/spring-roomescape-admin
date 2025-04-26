package roomescape.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO reservation(name, date, time_id) VALUES(?, ?, ?)",
                            new String[]{"id"});
                    ps.setString(1, reservation.getName());
                    ps.setDate(2, Date.valueOf(reservation.getDate()));
                    ps.setLong(3, reservation.getReservationTime().getId());
                    return ps;
                }
                , keyHolder);

        Long id = keyHolder.getKey().longValue();

        return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getReservationTime());
    }

    public boolean delete(Long id) {
        int deletedRow = jdbcTemplate.update(
                "DELETE FROM reservation WHERE id = ?",
                id
        );
        return deletedRow == 1;
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value"
                        + " FROM reservation as r"
                        + " INNER JOIN reservation_time as t"
                        + " ON r.time_id = t.id",
                (rs, rowNum) -> {
                    return new Reservation(
                            rs.getLong("reservation_id"),
                            rs.getString("name"),
                            rs.getDate("date").toLocalDate(),
                            new ReservationTime(
                                    rs.getLong("time_id"),
                                    rs.getTime("time_value").toLocalTime()
                            )
                    );
                }
        );
    }
}

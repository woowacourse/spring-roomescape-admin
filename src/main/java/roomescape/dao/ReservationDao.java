package roomescape.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Name;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationDate;
import roomescape.domain.reservation.ReservationTime;
import roomescape.dto.ReservationRequestDto;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Repository
public class ReservationDao {
    private static final DateTimeFormatter DATE_FORMMATER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter Time_FORMMATER = DateTimeFormatter.ofPattern("HH:mm");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ReservationDao() {
    }

    public List<Reservation> findAllReservation() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Long insertReservation(ReservationRequestDto reservationRequestDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation (name, date, time) values (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, reservationRequestDto.name());
            ps.setString(2, reservationRequestDto.date().format(DATE_FORMMATER));
            ps.setString(3, reservationRequestDto.time().format(Time_FORMMATER));
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Reservation findById(Long id) {
        String sql = "SELECT id, name, date, time FROM reservation WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
    }

    public void deleteReservation(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private final RowMapper<Reservation> reservationRowMapper = ((rs, rowNum) -> {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        LocalDate date = LocalDate.parse(rs.getString("date"), DATE_FORMMATER);
        LocalTime time = LocalTime.parse(rs.getString("time"), Time_FORMMATER);
        return new Reservation(id, new Name(name), new ReservationDate(date), new ReservationTime(time));
    });
}

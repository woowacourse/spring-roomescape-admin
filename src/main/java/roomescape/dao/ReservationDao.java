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
import java.util.List;
import java.util.Objects;

@Repository
public class ReservationDao {
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationRowMapper = ((rs, rowNum) -> {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        ReservationDate date = new ReservationDate(rs.getString("date"));
        ReservationTime time = new ReservationTime(rs.getString("time"));
        return new Reservation(id, new Name(name), date, time);
    });

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAllReservation() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Reservation findById(Long id) {
        String sql = "SELECT id, name, date, time FROM reservation WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
    }

    public Long addReservation(ReservationRequestDto reservationRequestDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation (name, date, time) values (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, reservationRequestDto.name());
            ps.setString(2, ReservationDate.formattedDate(reservationRequestDto.date()));
            ps.setString(3, ReservationTime.formattedTime(reservationRequestDto.time()));
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void deleteReservation(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

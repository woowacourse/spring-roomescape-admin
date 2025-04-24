package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationRequestDto;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> getAllReservations() {
        String sql = "SELECT r.id, r.name, r.date, r.time_id, t.start_at FROM reservation as r inner join reservation_time as t on r.time_id = t.id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("date"),
                new ReservationTime(rs.getLong("time_id"), rs.getString("start_at"))
        ));
    }

    public Reservation addReservation(ReservationRequestDto reservationRequestDto, ReservationTime reservationTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql, new String[]{"id"});
            ps.setString(1, reservationRequestDto.name());
            ps.setString(2, reservationRequestDto.date());
            ps.setLong(3, reservationRequestDto.timeId());
            return ps;
        }, keyHolder);
        return ReservationRequestDto.toEntity(Objects.requireNonNull(keyHolder.getKey()).longValue(), reservationRequestDto, reservationTime);
    }

    public int deleteReservation(Long id) {
        return jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}

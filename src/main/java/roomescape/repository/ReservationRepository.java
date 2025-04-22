package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.AddReservationDto;
import roomescape.model.Reservation;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> getAllReservations() {
        String sql = "SELECT id, name, date, time FROM reservation";
        List<Reservation> reservations = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Reservation reservation = new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("date"),
                    rs.getString("time")
            );
            return reservation;
        });
        return reservations;
    }

    public Reservation addReservation(AddReservationDto addReservationDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql, new String[]{"id"});
            ps.setString(1, addReservationDto.name());
            ps.setString(2, addReservationDto.date());
            ps.setString(3, addReservationDto.time());
            return ps;
        }, keyHolder);
        return AddReservationDto.toEntity(keyHolder.getKey().longValue(), addReservationDto);
    }

    public int deleteReservation(Long id) {
        return jdbcTemplate.update("delete from reservation where id = ?", Long.valueOf(id));
    }
}

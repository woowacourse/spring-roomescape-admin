package roomescape.reservation.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.reservation.model.Reservation;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class ReservationDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation insert(Reservation reservation) {
        Long reservationId = insertWithKeyHolder(reservation);
        return new Reservation(reservationId, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    private Long insertWithKeyHolder(Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setString(3, reservation.getTime().toString());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Reservation selectBy(Long id) {
        String sql = "select * from reservation where id = ?";
        return findReservationById(sql, id);
    }

    public List<Reservation> selectAll() {
        String sql = "select * from reservation";
        return jdbcTemplate.query(sql, rowMapperForReservation());
    }

    public void deleteBy(Long id) {
        String sql = "delete from reservation where id = ?";
        int deletedCount = jdbcTemplate.update(sql, id);
        if (deletedCount == 0) {
            throw new NoSuchElementException("해당 ID의 예약이 존재하지 않습니다. " + id);
        }
    }

    private RowMapper<Reservation> rowMapperForReservation() {
        return (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime()
        );
    }

    private Reservation findReservationById(String sql, Long id) {
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                rs.getTime("time").toLocalTime()
        ), id);
    }
}

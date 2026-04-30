package roomescape.reservation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class ReservationUpdatingDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReservationUpdatingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Long id, Reservation reservation) {
        String sql = "update reservation SET name = ?, date = ?, time_id = ? where id = ?";
        jdbcTemplate.update(sql, reservation.getName(), reservation.getDate(), reservation.getTime().getId(), id);
    }

    public int delete(Long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public Long insertWithKeyHolder(ReservationRequest reservationReq) {
        String sql = "insert into reservation(name, date, time_id) values(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"}
            );
            ps.setString(1, reservationReq.getName());
            ps.setObject(2, reservationReq.getDate());
            ps.setLong(3, reservationReq.getTimeId());
            return ps;
        }, keyHolder);

        Long id =  keyHolder.getKey().longValue();

        return id;
    }
}

package roomescape.reservation.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Reservation reservation) {
        String sql = "insert into reservation (id, name, date, time) values (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    sql, new String[]{"id"}
            );
            ps.setString(1, String.valueOf(reservation.getId()));
            ps.setString(2, reservation.getName());
            ps.setString(3, String.valueOf(reservation.getDate()));
            ps.setString(4, String.valueOf(reservation.getTime()));
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Reservation> findAll() {
        String sql = "select * from reservation";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> {
                    return new Reservation(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getDate("date").toLocalDate(),
                            rs.getTime("time").toLocalTime()
                    );
                });
    }
}

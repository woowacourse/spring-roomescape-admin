package roomescape.repositiory;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationRepository implements GeneralRepository<Reservation> {

    private final JdbcTemplate jdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    @Autowired
    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String query = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value "
                + "FROM reservation as r "
                + "inner join reservation_time as t "
                + "on r.time_id = t.id ";
        return jdbcTemplate.query(query, (rs, rowNum) -> new Reservation(
                rs.getLong("reservation_id"),
                rs.getString("name"),
                rs.getObject("date", LocalDate.class),
                new ReservationTime(rs.getLong("time_id"), rs.getObject("time_value", LocalTime.class))));
    }

    @Override
    public Reservation findById(Long id) {
        String query = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value "
                + "FROM reservation as r "
                + "inner join reservation_time as t "
                + "on r.time_id = t.id "
                + "where r.id = ?";
        return jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> new Reservation(
                        rs.getLong("reservation_id"),
                        rs.getString("name"),
                        rs.getObject("date", LocalDate.class),
                        new ReservationTime(
                                rs.getLong("time_id"),
                                rs.getObject("time_value", LocalTime.class)
                        )
                ));
    }

    @Override
    public Long add(Reservation reservation) {
        String query = "insert into RESERVATION (name, date, time) values (?,?,?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setObject(2, reservation.getDate());
            ps.setObject(3, reservation.getTime());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        reservation.setId(id);
        return id;
    }

    @Override
    public void delete(Long id) {
        String query = "delete from RESERVATION where id=?";
        jdbcTemplate.update(query, id);
    }
}

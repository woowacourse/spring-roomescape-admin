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
import roomescape.domain.dto.ReservationRequestDto;

@Repository
public class ReservationH2Repository implements ReservationRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public ReservationH2Repository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String query = "select id, name, date, time from RESERVATION";
        return jdbcTemplate.query(
                query, (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getObject("date", LocalDate.class),
                        rs.getObject("time", LocalTime.class)));
    }

    @Override
    public Reservation findById(Long id) {
        String query = "select id, name, date, time from RESERVATION where id = ?";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getObject("date", LocalDate.class),
                rs.getObject("time", LocalTime.class)
        ), id);
    }

    @Override
    public Long add(ReservationRequestDto reservation) {
        String query = "insert into RESERVATION (name, date, time) values (?,?,?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    query, new String[]{"id"});
            ps.setString(1, reservation.name());
            ps.setObject(2, reservation.date());
            ps.setObject(3, reservation.time());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public void delete(Long id) {
        String query = "delete from RESERVATION where id=?";
        jdbcTemplate.update(query, id);
    }
}

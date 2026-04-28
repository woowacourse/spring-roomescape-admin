package roomescape.reservation.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Reservation> findAll() {
        String sql = "select * from reservation";
        return jdbcTemplate.query(sql, reservationRowsMapper());
    }

    public Reservation save(String name, LocalDate date, LocalTime time) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, name);
            ps.setObject(2, date);
            ps.setObject(3, time);
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        return new Reservation(id, name, date, time);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Reservation> reservationRowsMapper() {
        return ((rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            LocalDate date = LocalDate.parse(rs.getString("date"));
            LocalTime time = LocalTime.parse(rs.getString("time"));
            return new Reservation(id, name, date, time);
        });
    }
}
package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class MemoryReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemoryReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservation";

        RowMapper<Reservation> mapper = (rs, rowNum) ->
                new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        LocalDate.parse(rs.getString("date")),
                        LocalTime.parse(rs.getString("time"))
                );

        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public Reservation findById(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";

        RowMapper<Reservation> mapper = (rs, rowNum) -> new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                LocalDate.parse(rs.getString("date")),
                LocalTime.parse(rs.getString("time"))
        );
        return jdbcTemplate.queryForObject(sql, mapper, id);
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator creator = con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.name());
            ps.setString(2, reservation.date().format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.setString(3, reservation.time().format(DateTimeFormatter.ISO_LOCAL_TIME));
            return ps;
        };
        jdbcTemplate.update(creator, keyHolder);

        return reservation.assignId(keyHolder.getKeyAs(Long.class));
    }

    @Override
    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }
}

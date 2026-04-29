package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationRequest;
import roomescape.model.Reservation;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long create(ReservationRequest request) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                    ps.setString(1, request.getName());
                    ps.setObject(2, request.getDate());
                    ps.setObject(3, request.getTime());
                    return ps;
                }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            return new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getObject("date", LocalDate.class),
                    resultSet.getObject("time", LocalTime.class)
            );
        });
    }

    public int delete(Long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

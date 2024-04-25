package roomescape.reservation.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.controller.ReservationResponse;
import roomescape.reservationtime.controller.ReservationTimeResponse;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationResponse> findAll() {
        String sql = "SELECT r.id as reservation_id, \n" +
                "    r.name, \n" +
                "    r.date, \n" +
                "    t.id as time_id, \n" +
                "    t.start_at as time_value \n" +
                "FROM reservation as r \n" +
                "inner join reservation_time as t \n" +
                "on r.time_id = t.id";
        return jdbcTemplate.query(sql, (resultSet, rowNumber) ->
                new ReservationResponse(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        LocalDate.parse(resultSet.getString("date")),
                        new ReservationTimeResponse(resultSet.getLong("time_id"),
                                LocalTime.parse(resultSet.getString("time_value")))));
    }

    public Long save(String name, LocalDate date, Long timeId) {
        String sql = "INSERT INTO reservation(name,date,time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, name);
            ps.setString(2, String.valueOf(date));
            ps.setLong(3, timeId);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

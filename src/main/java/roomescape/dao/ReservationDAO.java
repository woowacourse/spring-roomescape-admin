package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationResponse;

@Repository
public class ReservationDAO {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String sql = "select r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as start_at from reservation r inner join reservation_time t on r.time_id = t.id";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> {
                    return new Reservation(
                            rs.getLong("id"),
                            rs.getString("name"),
                            LocalDate.parse(rs.getString("date")),
                            new ReservationTime(rs.getLong("id"),
                                    LocalTime.parse(rs.getString("start_at"), DateTimeFormatter.ofPattern("HH:mm")))
                    );
                });
    }

    public ReservationResponse create(Reservation reservation) {
//        MapSqlParameterSource parameters = new MapSqlParameterSource()
//                .addValue("name", reservation.getName())
//                .addValue("date", reservation.getDate())
//                .addValue("time_id", reservation.getReservationTime().getId());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate());
        parameters.put("time_id", reservation.getTime().getId());

        Long id = (long) simpleJdbcInsert.executeAndReturnKey(parameters);
        return ReservationResponse.from(id, reservation.getName(), reservation.getDate(),
                reservation.getTime());
    }

    public int delete(Long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

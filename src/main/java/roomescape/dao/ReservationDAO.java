package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
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

    public List<Reservation> read() {
        String sql = "SELECT id, name, date, time from reservation";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> {
                    return new Reservation(
                            rs.getLong("id"),
                            rs.getString("name"),
                            LocalDate.parse(rs.getString("date")),
                            LocalTime.parse(rs.getString("time"))
                    );
                });
    }

    public ReservationResponse insert(Reservation reservation) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time", reservation.getTime());

        Long id = (long) simpleJdbcInsert.execute(parameters);
        return ReservationResponse.from(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public int delete(Long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

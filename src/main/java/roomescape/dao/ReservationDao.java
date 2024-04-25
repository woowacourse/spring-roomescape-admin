package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> getReservations() {
        String sql = "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.start_at "
                + "AS time_value FROM reservation AS r INNER JOIN reservation_time AS t ON r.time_id = t.id";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    return new Reservation(
                            resultSet.getLong("reservation_id"),
                            new Name(resultSet.getString("name")),
                            LocalDate.parse(resultSet.getString("date")),
                            new ReservationTime(
                                    resultSet.getLong("time_id"),
                                    LocalTime.parse(resultSet.getString("time_value"))
                            ));
                });
    }

    public Reservation add(ReservationRequest reservationRequest) {
        Long timeId = reservationRequest.getTimeId();
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", reservationRequest.getName())
                .addValue("date", reservationRequest.getDate())
                .addValue("time_id", timeId);

        LocalTime timeValue = LocalTime.parse(jdbcTemplate.queryForObject(
                "SELECT start_at FROM reservation_time WHERE id = (?)", String.class, timeId));

        Long id = jdbcInsert.executeAndReturnKey(parameterSource).longValue();
        return reservationRequest.toReservation(id, new ReservationTime(timeId, timeValue));
    }

    public void delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

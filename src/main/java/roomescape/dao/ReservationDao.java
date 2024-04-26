package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("reservation_id"),
            new Name(resultSet.getString("name")),
            LocalDate.parse(resultSet.getString("date")),
            new ReservationTime(
                    resultSet.getLong("time_id"),
                    LocalTime.parse(resultSet.getString("time_value"))
            ));
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String sql = """
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                FROM reservation as r
                inner join reservation_time as t
                on r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, RESERVATION_ROW_MAPPER);
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

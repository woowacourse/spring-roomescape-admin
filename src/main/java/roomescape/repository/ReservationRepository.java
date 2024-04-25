package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDto;
import roomescape.domain.TimeSlot;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final RowMapper<Reservation> rowMapper =
            (resultSet, rowNum) -> new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    LocalDate.parse(resultSet.getString("date")),
                    new TimeSlot(resultSet.getLong("time_id"), resultSet.getString("time_value"))
            );


    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingColumns("name", "date", "time_id")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> read() {
        String sql = """
                SELECT
                r.id as reservation_id,
                r.name,
                r.date,
                t.id as time_id,
                t.start_at as time_value
                FROM reservation as r inner join reservation_time as t
                on r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Long create(ReservationDto reservationDto) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", reservationDto.name())
                .addValue("date", reservationDto.date())
                .addValue("time_id", reservationDto.timeId());
        return jdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    public void delete(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}

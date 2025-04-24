package roomescape.dao;

import java.sql.Time;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationReqDto;
import roomescape.dto.ReservationResDto;
import roomescape.dto.ReservationTimeResDto;

@Repository
public class ReservationDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationResDto> findAllReservations() {
        String sql = """
                SELECT
                r.id as reservation_id,
                r.name,
                r.date,
                t.id as time_id,
                t.start_at as time_value
                FROM reservation AS r
                INNER JOIN reservation_time AS t
                ON r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new ReservationResDto(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                new ReservationTimeResDto(
                        resultSet.getLong("time_id"),
                        resultSet.getTime("time_value").toLocalTime()
                )
        ));
    }

    public ReservationResDto addAndGet(ReservationReqDto dto) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingColumns("name", "date", "time_id")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = Map.of(
                "name", dto.name(),
                "date", dto.date(),
                "time_id", dto.timeId()
        );
        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);

        Time startAt = jdbcTemplate.queryForObject("SELECT start_at FROM reservation_time WHERE id = ?", Time.class, dto.timeId());

        ReservationTimeResDto timeRes = new ReservationTimeResDto((long) dto.timeId(), startAt.toLocalTime());
        return new ReservationResDto(id.longValue(), dto.name(), dto.date(), timeRes);
    }

    public void deleteById(Long id) {
        int rows = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
        if (rows == 0) {
            throw new EmptyResultDataAccessException(rows);
        }
    }
}

package roomescape.repository;

import java.time.LocalTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dto.CreateReservationTimeDto;
import roomescape.entity.ReservationTime;

@Repository
public class ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime add(CreateReservationTimeDto createReservationTimeDto) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("reservationTime")
                .usingColumns("startAt")
                .usingGeneratedKeyColumns("id");

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("startAt", createReservationTimeDto.startAt());

        Long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();

        return findById(id);
    }

    private ReservationTime findById(Long id) {
        String sql = "SELECT id, startAt FROM reservationTime WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getLong("id"),
                        resultSet.getObject("startAt", LocalTime.class)
                ), id);
    }
}

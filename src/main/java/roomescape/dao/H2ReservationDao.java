package roomescape.dao;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationCreationDto;

@Repository
public class H2ReservationDao implements ReservationDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public H2ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id")
                .usingColumns("name", "date", "time_id");
    }

    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("date"),
            new ReservationTime(
                    rs.getLong("time_id"),
                    rs.getString("time_value")
            )
    );

    @Override
    public List<Reservation> findAll() {
        String sql = """
            SELECT
                r.id as reservation_id,\s
                r.name,\s
                r.date,\s
                t.id as time_id,\s
                t.start_at as time_value\s
            FROM reservation as r\s
            inner join reservation_time as t\s
            on r.time_id = t.id""";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Reservation add(ReservationCreationDto request) {
        ReservationTime reservationTime = request.reservationTime();

        Map<String, Object> parameters = Map.of(
                "name", request.name(),
                "date", request.date(),
                "time_id", reservationTime.getId()
        );
        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);
        return new Reservation(
                key.longValue(), request.name(),
                request.date(), reservationTime
        );
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM reservation";
        jdbcTemplate.update(sql);
    }

    @Override
    public boolean isExist(Long id) {
        String sql = "SELECT id FROM reservation WHERE id = ? LIMIT 1";
        return !jdbcTemplate.query(sql,
                (rs, rowNum) -> rs.getInt("id"), id
        ).isEmpty();
    }
}

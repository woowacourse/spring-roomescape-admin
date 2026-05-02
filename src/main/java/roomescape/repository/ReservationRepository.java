package roomescape.repository;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.dto.ReservationSaveDto;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        String sql = "select r.id as reservation_id, r.name, r.date, rt.id as time_id, rt.start_at from reservation r inner join reservation_time rt on r.time_id = rt.id";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("reservation_id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        new ReservationTime(resultSet.getLong("time_id"), resultSet.getString("start_at"))));
    }

    public Reservation save(ReservationSaveDto dto, ReservationTime find) {
        Map<String, Object> params = Map.of(
                "name", dto.getName(),
                "date", dto.getDate(),
                "time_id", find.getId()
        );

        long generatedKey = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return new Reservation(generatedKey, dto.getName(), dto.getDate(), find);
    }

    public void deleteById(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}

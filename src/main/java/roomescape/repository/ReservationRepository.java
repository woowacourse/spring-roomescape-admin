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
                (resultSet, rowNum) -> Reservation.of(
                        resultSet.getLong("reservation_id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        ReservationTime.of(resultSet.getLong("time_id"), resultSet.getString("start_at"))));
    }

    public Reservation save(ReservationSaveDto dto) {
        Map<String, Object> params = Map.of(
                "name", dto.getName(),
                "date", dto.getDate(),
                "time_id", dto.getReservationTime().getId()
        );

        long generatedKey = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return Reservation.of(generatedKey, dto.getName(), dto.getDate(), dto.getReservationTime());
    }

    public void deleteById(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}

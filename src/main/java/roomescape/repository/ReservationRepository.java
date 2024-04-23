package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationRepositoryDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ReservationRepositoryDto add(ReservationRepositoryDto reservationRepositoryDto) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservationRepositoryDto.name());
        parameters.put("date", reservationRepositoryDto.date());
        parameters.put("time_id", reservationRepositoryDto.timeId());

        Long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return new ReservationRepositoryDto(id, reservationRepositoryDto.name(), reservationRepositoryDto.date(), reservationRepositoryDto.timeId());
    }

    public List<ReservationRepositoryDto> findAll() {
        String SQL = "SELECT r.id, r.name, r.date, t.id AS time_id, t.start_at  " +
                "FROM reservation AS r " +
                "INNER JOIN reservation_time AS t " +
                "ON r.time_id = t.id";

        return jdbcTemplate.query(SQL, (rs, rowNum) -> new ReservationRepositoryDto(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("date"),
                    rs.getLong("time_id")
            )
        );
    }

    public void remove(Long id) {
        String SQL = "DELETE FROM RESERVATION WHERE id = ?";
        jdbcTemplate.update(SQL, id);
    }
}

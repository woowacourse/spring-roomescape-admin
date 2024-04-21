package roomescape.reservations;

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
        String SQL = "SELECT \n" +
                "    r.id as reservation_id, \n" +
                "    r.name, \n" +
                "    r.date, \n" +
                "    t.id as time_id, \n" +
                "    t.start_at as time_value \n" +
                "FROM reservation as r \n" +
                "inner join reservation_time as t \n" +
                "on r.time_id = t.id";

        return jdbcTemplate.query(SQL, (rs, rowNum) -> {
            ReservationRepositoryDto reservationRepositoryDto = new ReservationRepositoryDto(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("date"),
                    rs.getLong("time_id")
            );
            return reservationRepositoryDto;
        });
    }

    public void remove(Long id) {
        String SQL = "DELETE FROM RESERVATION WHERE id = ?";
        jdbcTemplate.update(SQL, id);
    }
}

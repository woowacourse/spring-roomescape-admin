package roomescape.dao;

import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class UpdatingDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public UpdatingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
            .withTableName("reservation")
            .usingGeneratedKeyColumns("id");
    }

    public void saveReservation(Reservation reservation) {
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("name", reservation.getPersonName());
        parameters.put("date", reservation.getDate());
        parameters.put("time_id", reservation.getTimeId());
        Number newId = insertActor.executeAndReturnKey(parameters);
        reservation.setId(newId.longValue());
    }

    public void deleteReservation(Long id) {
        String query = "delete from reservation where id = ?";
        jdbcTemplate.update(query, id);
    }
}

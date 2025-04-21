package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

@Repository
public class H2ReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public H2ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM Reservation";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        LocalDate.parse(resultSet.getString("date")),
                        LocalTime.parse(resultSet.getString("time"))
                )
        );
    }

    @Override
    public Reservation add(Reservation reservation) {
        Map<String, String> params = new HashMap<>();
        params.put("name", reservation.getName());
        params.put("date", reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        params.put("time", reservation.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        int id = jdbcInsert.executeAndReturnKey(params).intValue();
        return reservation.createWithId(id);
    }

    @Override
    public void removeById(int id) {
        String sql = "DELETE FROM Reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

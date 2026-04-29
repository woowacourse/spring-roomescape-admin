package roomescape;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name, date, time FROM reservation",
                ((rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        LocalDate.parse(rs.getString("date")),
                        LocalTime.parse(rs.getString("time"))
                ))
        );
    }

    public Reservation save(String name, LocalDate reservationDate, LocalTime reservationTime) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");

        long savedId = simpleJdbcInsert.executeAndReturnKey(Map.of(
                "name", name,
                "date", reservationDate.toString(),
                "time", reservationTime.toString()
        )).longValue();

        return findById(savedId);
    }

    public void deleteById(Long id) {
        int updatedRow = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
        if (updatedRow == 0) {
            throw new NoSuchElementException("[ERROR] 해당 id의 Reservation이 존재하지 않습니다.");
        }
    }

    private Reservation findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, name, date, time FROM reservation WHERE id = ?",
                (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        LocalDate.parse(rs.getString("date")),
                        LocalTime.parse(rs.getString("time"))
                ),
                id
        );
    }
}

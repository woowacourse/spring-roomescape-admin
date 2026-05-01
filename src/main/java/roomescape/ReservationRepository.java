package roomescape;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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
                "SELECT r.id, r.name, r.date, t.id AS time_id, t.start_at AS start_at " +
                        "FROM reservation r " +
                        "JOIN reservation_time t ON r.time_id = t.id ",
                (rs, rowNum) -> getReservation(rs)
        );
    }

    public Reservation save(String name, LocalDate reservationDate, Long timeId) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");

        long savedId = simpleJdbcInsert.executeAndReturnKey(Map.of(
                "name", name,
                "date", reservationDate.toString(),
                "time_id", timeId
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
                "SELECT r.id, r.name, r.date, t.id AS time_id, t.start_at AS start_at " +
                        "FROM reservation r " +
                        "JOIN reservation_time t ON r.time_id = t.id " +
                        "WHERE r.id = ?",
                (rs, rowNum) -> getReservation(rs),
                id
        );
    }

    private static Reservation getReservation(ResultSet rs) throws SQLException {
        return new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                LocalDate.parse(rs.getString("date")),
                new ReservationTime(
                        rs.getLong("time_id"),
                        LocalTime.parse(rs.getString("start_at"))
                )
        );
    }
}

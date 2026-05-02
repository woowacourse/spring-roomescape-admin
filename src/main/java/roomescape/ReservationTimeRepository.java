package roomescape;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime save(LocalTime startAt) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");

        long savedId = simpleJdbcInsert.executeAndReturnKey(Map.of(
                "start_at", startAt.toString()
        )).longValue();

        return findById(savedId);
    }

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query(
                "SELECT id, start_at FROM reservation_time",
                ((rs, rowNum) -> getReservationTime(rs))
        );
    }

    public void deleteById(Long id) {
        int updatedRow = jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
        if (updatedRow == 0) {
            throw new NoSuchElementException("[ERROR] 해당 id의 ReservationTime이 존재하지 않습니다.");
        }
    }

    private ReservationTime findById(long id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, start_at FROM reservation_time WHERE id = ?",
                (rs, rowNum) -> getReservationTime(rs),
                id
        );
    }

    private static ReservationTime getReservationTime(ResultSet rs) throws SQLException {
        return new ReservationTime(
                rs.getLong("id"),
                LocalTime.parse(rs.getString("start_at"))
        );
    }
}

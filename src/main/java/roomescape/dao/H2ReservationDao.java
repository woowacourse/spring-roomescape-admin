package roomescape.dao;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

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

    private static final RowMapper<Reservation> rowMapper = (rs, rowNum) -> new Reservation(
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
                r.id as reservation_id,
                r.name,
                r.date,
                t.id as time_id,
                t.start_at as time_value
            FROM reservation as r
            inner join reservation_time as t
            on r.time_id = t.id""";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Reservation add(Reservation reservation) {
        ReservationTime reservationTime = reservation.getTime();

        Map<String, Object> parameters = Map.of(
                "name", reservation.getName(),
                "date", reservation.getDate(),
                "time_id", reservationTime.getId()
        );
        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);
        return new Reservation(
                key.longValue(),
                reservation.getName(),
                reservation.getDate(),
                reservationTime
        );
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean isExist(Long id) {
        String sql = "SELECT id FROM reservation WHERE id = ? LIMIT 1";
        return checkMatchedRowExistByIdColumn(sql, id.toString());
    }

    @Override
    public boolean isExistByTimeId(Long timeId) {
        String sql = "SELECT id FROM reservation WHERE time_id = ? LIMIT 1";
        return checkMatchedRowExistByIdColumn(sql, timeId.toString());
    }

    private boolean checkMatchedRowExistByIdColumn(String singleParameterizedSql, String param) {
        return !jdbcTemplate.query(singleParameterizedSql,
                (rs, rowNum) -> rs.getInt("id"), param
        ).isEmpty();
    }
}

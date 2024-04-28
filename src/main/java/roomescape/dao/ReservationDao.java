package roomescape.dao;

import java.sql.Date;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Component
public class ReservationDao {

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (resultSet, rowNum) -> {
        Long timeId = resultSet.getLong("time_id");
        LocalTime time = resultSet.getTime("start_at").toLocalTime();

        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                new ReservationTime(timeId, time));
    };

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAllReservations() {
        String sql = """
                select
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                from reservation as r
                inner join reservation_time as t
                on r.time_id = t.id
                """;

        return jdbcTemplate.query(sql, RESERVATION_ROW_MAPPER);
    }

    public Reservation findReservation(Long id) {
        String sql = """
                select
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                from reservation as r
                inner join reservation_time as t
                on r.time_id = t.id
                where r.id = ?
                """;

        return jdbcTemplate.queryForObject(sql, RESERVATION_ROW_MAPPER, id);
    }

    public Long saveReservation(Reservation reservation) {
        return simpleJdbcInsert.executeAndReturnKey(
                        Map.of(
                                "name", reservation.getName(),
                                "date", Date.valueOf(reservation.getDate()),
                                "time_id", reservation.getTime().getId()
                        ))
                .longValue();
    }

    public void deleteReservation(Long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }

    public boolean hasReservationOf(Long id) {
        String sql = "SELECT COUNT(*) FROM RESERVATION WHERE time_id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);

        return count > 0;
    }
}

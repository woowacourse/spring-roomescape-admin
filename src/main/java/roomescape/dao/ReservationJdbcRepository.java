package roomescape.dao;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReserveTime;

@Repository("JdbcRepository")
public class ReservationJdbcRepository implements ReservationRepository {

    private static final RowMapper<Reservation> ROW_MAPPER = (resultSet, rowNum) ->
            new Reservation(
                    Long.valueOf(resultSet.getString("id")),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    resultSet.getString("time")
            );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingColumns("name", "date", "time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Reservation addReservation(Reservation reservation) {
        Name name = reservation.getName();
        ReserveTime reserveTime = reservation.getReserveTime();
        Map<String, String> parameters = Map.of(
                "name", name.asText(),
                "date", reserveTime.getDateAsText(),
                "time", reserveTime.getTimeAsText()
        );
        Number key = jdbcInsert.executeAndReturnKey(parameters);
        return new Reservation(key.longValue(), reservation);
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select id, name, date, time from reservation";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "select count(*) from reservation where id = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, id) > 0;
    }

    @Override
    public void deleteAll() {
        String sql = "delete from reservation";
        jdbcTemplate.update(sql);
    }
}

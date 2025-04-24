package roomescape.dao;

import java.time.LocalDate;
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

    private static final String FIND_ALL_SQL = """
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
    private static final String DELETE_BY_ID_SQL = "delete from reservation where id = ?";
    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (resultSet, row) ->
            new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getObject("date", LocalDate.class),
                    new ReservationTime(
                            resultSet.getLong("id"),
                            resultSet.getObject("start_at", LocalTime.class)
                    )
            );

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                FIND_ALL_SQL,
                RESERVATION_ROW_MAPPER
        );
    }

    public Reservation save(Reservation reservation) {
        SimpleJdbcInsert jdbcInsert = createJdbcInsert();
        Map<String, Object> params = convertToParams(reservation);

        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return new Reservation(id, reservation);
    }

    public boolean deleteById(Long id) {
        int updatedRows = jdbcTemplate.update(DELETE_BY_ID_SQL, id);
        return updatedRows > 0;
    }

    private SimpleJdbcInsert createJdbcInsert() {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    private Map<String, Object> convertToParams(final Reservation reservation) {
        return Map.of(
                "name", reservation.getName(),
                "date", reservation.getDate(),
                "time_id", reservation.getTime().getId()
        );
    }
}

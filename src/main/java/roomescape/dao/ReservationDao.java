package roomescape.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;

@Component
public class ReservationDao {

    private static final String FIND_ALL_SQL = "select * from reservation";
    private static final String DELETE_BY_ID_SQL = "delete from reservation where id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> reservationMapper = (resultSet, row) ->
            new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getObject("datetime", LocalDateTime.class)
            );

    @Autowired
    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservations findAll() {
        return new Reservations(jdbcTemplate.query(
                FIND_ALL_SQL,
                reservationMapper
        ));
    }

    public Reservation save(Reservation reservation) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>(2);
        params.put("name", reservation.getName());
        params.put("datetime", reservation.getDateTime());

        Long id = jdbcInsert.executeAndReturnKey(params).longValue();

        return new Reservation(id, reservation);
    }

    public boolean deleteById(Long id) {
        int updatedRows = jdbcTemplate.update(DELETE_BY_ID_SQL, id);
        return updatedRows > 0;
    }
}

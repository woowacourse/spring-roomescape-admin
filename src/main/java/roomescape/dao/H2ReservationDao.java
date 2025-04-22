package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class H2ReservationDao implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public H2ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> Reservation.of(
        resultSet.getLong("id"),
        resultSet.getString("name"),
        resultSet.getObject("datetime", LocalDateTime.class)
    );

    public List<Reservation> findAll() {
        String sql = "SELECT id, name, datetime FROM reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public Reservation insert(final Reservation reservation) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getCustomerName());
        parameters.put("datetime", reservation.getReservationDateTime());
        Number savedId = simpleJdbcInsert.executeAndReturnKey(parameters);

        return Reservation.of(savedId.longValue(), reservation.getCustomerName(), reservation.getReservationDateTime());
    }
    
    public boolean deleteById(final Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int deletedRows = jdbcTemplate.update(sql, id);
        return deletedRows > 0;
    }
}

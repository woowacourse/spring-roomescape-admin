package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;

@Repository
public class JdbcTemplateReservationRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateReservationRepository(JdbcTemplate jdbcTemplate, DataSource source) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(source)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getDate("date").toLocalDate(),
            resultSet.getTime("time").toLocalTime()
    );

    @Override
    public Reservation save(Reservation reservationRequest) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", reservationRequest.getName());
        params.put("date", reservationRequest.getDate());
        params.put("time", reservationRequest.getTime());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return new Reservation(id, reservationRequest);
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT id, name, date, time FROM reservation", reservationRowMapper);
    }

    @Override
    public void deleteById(Long id) {
        int rowCount = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
        if (rowCount == 0) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
    }
}

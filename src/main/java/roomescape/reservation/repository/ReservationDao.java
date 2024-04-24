package roomescape.reservation.repository;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationTime;

@Component
public class ReservationDao implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Reservation save(final Reservation reservation) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(reservation);
        long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Reservation(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value " +
                        "FROM reservation as r " +
                        "INNER JOIN reservation_time as t on r.time_id = t.id",
                (resultSet, rowNum) -> {
                    return new Reservation(
                            resultSet.getLong("reservation_id"),
                            resultSet.getString("name"),
                            resultSet.getDate("date").toLocalDate(),
                            new ReservationTime(resultSet.getLong("time_id"),
                                    resultSet.getTime("time_value").toLocalTime()
                            )
                    );
                }
        );
    }

    @Override
    public boolean deleteById(final long reservationId) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int updateId = jdbcTemplate.update(sql, reservationId);
        return updateId != 0;
    }
}

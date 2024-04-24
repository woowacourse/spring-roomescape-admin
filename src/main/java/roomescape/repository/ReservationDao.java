package roomescape.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation save(ReservationRegisterDetail registerDetail) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", registerDetail.name())
                .addValue("date", registerDetail.date())
                .addValue("time_id", registerDetail.timeId());
        long savedReservationId = insertActor.executeAndReturnKey(parameters).longValue();

        return registerDetail.toEntity(savedReservationId);
    }

    public List<Reservation> findAll() {
        String sql = """
                SELECT 
                    r.id AS reservation_id,
                    r.name,
                    r.date,
                    t.id AS time_id,
                    t.start_at AS time_value
                FROM reservation AS r
                INNER JOIN reservation_time as t
                ON r.time_id = t.id
                """;

        return jdbcTemplate.query(sql, reservationRowMapper());
    }

    public void deleteById(long reservationId) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        jdbcTemplate.update(sql, reservationId);
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        resultSet.getString("time_value")
                )
        );
    }
}

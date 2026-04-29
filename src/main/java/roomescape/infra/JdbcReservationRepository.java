package roomescape.infra;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.entity.Reservation;
import roomescape.domain.entity.ReservationTime;
import roomescape.repository.ReservationRepository;

import java.time.LocalTime;
import java.util.List;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
        Long timeId = resultSet.getLong("time_id");
        LocalTime startAt = resultSet.getTime("start_at").toLocalTime();
        ReservationTime reservationTime = new ReservationTime(timeId, startAt);
        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                reservationTime
        );
    };

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long save(Reservation reservation) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", reservation.getMemberName())
                .addValue("date", reservation.getDate())
                .addValue("time_id", reservation.getTime().getId());

        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    @Override
    public List<Reservation> findAll() {
        String select = """
                SELECT
                    r.id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at
                FROM reservation as r
                INNER JOIN reservation_time as t
                  ON r.time_id = t.id
                """;

        return jdbcTemplate.query(select, rowMapper);
    }

    @Override
    public void delete(Long id) {
        String delete = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(delete, id);
    }
}

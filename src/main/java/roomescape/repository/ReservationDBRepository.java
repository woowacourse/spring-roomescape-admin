package roomescape.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.util.CustomDateTimeFormatter;

@Repository
public class ReservationDBRepository implements ReservationRepository {

    private final String findAllReservationQuery = """
            SELECT
                r.id as reservation_id,
                r.name,
                r.date,
                t.id as time_id,
                t.start_at as time_value
            FROM reservation as r
            inner join reservation_time as t
            on r.time_id = t.id""";

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) ->
            new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    CustomDateTimeFormatter.getLocalDate(rs.getString("date")),
                    new ReservationTime(
                            rs.getLong("time_id"),
                            CustomDateTimeFormatter.getLocalTime(rs.getString("time_value"))
                    )
            );

    private final JdbcTemplate jdbcTemplate;

    public ReservationDBRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findReservations() {
        return jdbcTemplate.query(findAllReservationQuery, reservationRowMapper);
    }

    public Optional<Reservation> findReservationById(Long createdReservationId) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(findAllReservationQuery + " WHERE r.id = ?",
                        reservationRowMapper,
                        createdReservationId)
        );
    }

    public Long createReservation(ReservationCreateRequest reservationCreateRequest) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
        return simpleJdbcInsert.executeAndReturnKey(Map.of(
                "name", reservationCreateRequest.name(),
                "date", reservationCreateRequest.date(),
                "time_id", reservationCreateRequest.timeId()
        )).longValue();
    }

    public void deleteReservationById(Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}

package roomescape.repository;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.util.CustomDateTimeFormatter;

@Repository
public class ReservationRepository {


    @Autowired
    private JdbcTemplate jdbcTemplate;
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

    public List<Reservation> findReservations() {
        return jdbcTemplate.query(findAllReservationQuery, reservationRowMapper);
    }

    public Reservation findReservationById(Long createdReservationId) {
        System.out.println(createdReservationId);
        List<Reservation> reservations = findReservations();
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
        return jdbcTemplate.queryForObject(findAllReservationQuery + " WHERE r.id = ?",
                reservationRowMapper,
                createdReservationId);
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
}

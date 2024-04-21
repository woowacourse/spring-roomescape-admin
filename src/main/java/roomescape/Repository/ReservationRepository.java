package roomescape.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;

@Repository
public class ReservationRepository {
    private final AtomicLong id = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> {
        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        );
    };

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(id.getAndIncrement(), reservationRequest.getName(),
                reservationRequest.getDate(), reservationRequest.getTime());
        reservations.add(reservation);
        return ReservationResponse.of(reservation);
    }

    public List<ReservationResponse> readReservations() {
        String sql = "select id, name, date, time from reservation";
        List<Reservation> reservations = jdbcTemplate.query(sql, actorRowMapper);
        return reservations.stream()
                .map(ReservationResponse::of)
                .toList();
    }

    public boolean deleteReservation(Long id) {
        return reservations.removeIf(reservation -> reservation.equalId(id));
    }
}

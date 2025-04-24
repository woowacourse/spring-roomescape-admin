package roomescape.reservation;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.dto.ReservationRequest;
import roomescape.reservationTime.ReservationTime;

public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation saveReservation(ReservationRequest wantToSaveReservationRequest) {
        String insertQuery = "INSERT INTO RESERVATION (name, date, time_id) values (?,?,?)";
        String findQuery = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        ReservationTime wantToFindReservationTime = jdbcTemplate.queryForObject(
                findQuery,
                (result, rowNum) -> {
                    ReservationTime reservationTime = new ReservationTime(
                            result.getLong("id"),
                            result.getTime("start_at").toLocalTime()
                    );
                    return reservationTime;
                }
                , wantToSaveReservationRequest.getTimeId());

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    insertQuery, new String[]{"id"});
            preparedStatement.setString(1, wantToSaveReservationRequest.getName());
            preparedStatement.setDate(2, java.sql.Date.valueOf(wantToSaveReservationRequest.getDate()));
            preparedStatement.setLong(3, wantToSaveReservationRequest.getTimeId());
            return preparedStatement;
        }, keyHolder);

        Reservation wantToSaveReservation = new Reservation(
                wantToSaveReservationRequest.getName(), wantToSaveReservationRequest.getDate(),
                wantToFindReservationTime
        );

        return Reservation.toEntity(wantToSaveReservation, keyHolder.getKey().longValue());
    }

    @Override
    public void deleteReservation(Long wantToDeleteId) {
        String query = "DELETE FROM RESERVATION WHERE id = ?";
        jdbcTemplate.update(query, wantToDeleteId);
    }

    @Override
    public List<Reservation> findAllReservations() {
        String query = """
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                FROM reservation as r
                inner join reservation_time as t
                on r.time_id = t.id""";

        return jdbcTemplate.query(
                query,
                (result, rowNum) -> {
                    ReservationTime wantToFindReservationTime = new ReservationTime(
                            result.getLong("time_id"),
                            result.getTime("start_at").toLocalTime()
                    );

                    Reservation reservation = new Reservation(
                            result.getLong("id"),
                            result.getString("name"),
                            result.getDate("date").toLocalDate(),
                            wantToFindReservationTime
                    );
                    return reservation;
                }
        );
    }

    public boolean isExistReservation(ReservationRequest reservationRequest) {
        String query = "SELECT COUNT(*) FROM RESERVATION WHERE DATE = ? AND TIME_ID = ?";
        int count = jdbcTemplate.queryForObject(query, Integer.class,
                reservationRequest.getDate(), reservationRequest.getTimeId());
        return count > 0;
    }

}

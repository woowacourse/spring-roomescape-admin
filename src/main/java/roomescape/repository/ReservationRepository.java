package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@Repository
public class ReservationRepository {

    private static final String DUPLICATE_TIME_EXCEPTION = "해당 시간대는 이미 예약되어 있습니다.";
    private static final String INVALID_ID_EXCEPTION = "해당 아이디는 존재하지 않습니다.";
    private static final int EMPTY_ROW_COUNT = 0;

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("reservation_id"),
            resultSet.getString("name"),
            LocalDate.parse(resultSet.getString("date")),
            new ReservationTime(
                    resultSet.getLong("time_id"),
                    LocalTime.parse(resultSet.getString("time_value"))
            )
    );

    public Reservation readReservationById(final Long id) {
        String sql = """
                SELECT 
                    r.id AS reservation_id, 
                    r.name, 
                    r.date, 
                    t.id AS time_id, 
                    t.start_at AS time_value 
                FROM reservation AS r 
                INNER JOIN reservation_time AS t 
                ON r.time_id = t.id 
                WHERE r.id = ?
                """;
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<Reservation> readAllReservations() {
        String sql = """
                SELECT
                    r.id AS reservation_id, 
                    r.name, 
                    r.date, 
                    t.id AS time_id, 
                    t.start_at AS time_value 
                FROM reservation AS r 
                INNER JOIN reservation_time AS t 
                ON r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, rowMapper);
    }

    public synchronized Reservation createReservation(final Reservation reservation) {
        checkDuplicates(reservation);

        Number id = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(
                        Map.of(
                                "name", reservation.getName(),
                                "date", reservation.getDate().toString(),
                                "time_id", reservation.getTime().getId()
                        )
                );
        return readReservationById(id.longValue());
    }

    public synchronized void deleteReservationById(final Long id) {
        String sql = "delete from reservation where id = ?";
        int rowCount = jdbcTemplate.update(sql, id);

        if (rowCount == EMPTY_ROW_COUNT) {
            throw new IllegalArgumentException(INVALID_ID_EXCEPTION);
        }
    }

    private void checkDuplicates(final Reservation reservation) {
        List<Reservation> reservations = readAllReservations();

        boolean isDuplicate = reservations.stream()
                .anyMatch(reserve -> reserve.getDate().equals(reservation.getDate()) &&
                        reserve.getTime().getId().equals(reservation.getTime().getId())
                );

        if (isDuplicate) {
            throw new IllegalArgumentException(DUPLICATE_TIME_EXCEPTION);
        }
    }
}

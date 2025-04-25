package roomescape.reservation;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.time.ReservationTime;

@Repository
public class ReservationJdbcDao implements ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationJdbcDao(
            @Autowired JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long saveReservation(final Reservation reservation, final Long timeId) {
        final HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.name());
        parameters.put("date", reservation.date());
        parameters.put("time_id", timeId);

        final Number id = simpleJdbcInsert.executeAndReturnKey(parameters);
        return id.longValue();
    }

    @Override
    public List<Reservation> findAllReservation() {
        final String query =
                "SELECT R.id, R.name, R.date, T.id AS time_id, T.start_at AS start_at "
                        + "FROM RESERVATION AS R INNER JOIN RESERVATION_TIME AS T "
                        + "ON R.time_id=T.id";

        return jdbcTemplate.query(query, reservationMapper());
    }

    @Override
    public Reservation findReservationById(final Long id) {
        final String query =
                "SELECT R.id, R.name, R.date, T.id AS time_id, T.start_at AS start_at "
                        + "FROM RESERVATION AS R INNER JOIN RESERVATION_TIME AS T "
                        + "ON R.time_id=T.id "
                        + "WHERE R.id=?";

        return jdbcTemplate.queryForObject(query, reservationMapper(), id);
    }

    private RowMapper<Reservation> reservationMapper() {
        return (rs, rowNum) -> {
            return new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getDate("date").toLocalDate(),
                    new ReservationTime(rs.getLong("time_id"), rs.getTime("start_at").toLocalTime())
            );
        };
    }

    @Override
    public void deleteReservationById(final long id) {
        final String query = "DELETE FROM RESERVATION WHERE ID=?";
        final int updatedCount = jdbcTemplate.update(query, id);
        validateUpdateSuccess(updatedCount);
    }

    private static void validateUpdateSuccess(final int updatedCount) {
        if (updatedCount == 0) {
            throw new IllegalArgumentException("수정/삭제된 reservation이 존재하지 않습니다.");
        }
    }
}

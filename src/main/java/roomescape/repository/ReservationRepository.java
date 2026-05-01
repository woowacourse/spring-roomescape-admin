package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.exception.EntityNotFoundException;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationRepository(
            JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation persist(Reservation reservation) {
        Number id = simpleJdbcInsert.executeAndReturnKey(Map.of(
                "name", reservation.getName(),
                "date", reservation.getDate(),
                "time_id", reservation.getTimeId()
        ));

        return reservation.with(id.longValue());
    }

    public List<Reservation> findAll() {
        String findSql = "SELECT r.*, rt.start_at"
                + " FROM reservation r"
                + " JOIN reservation_time rt"
                + " ON r.time_id = rt.id";

        return jdbcTemplate.query(findSql, reservationRowMapper());
    }

    public void delete(long id) {
        String deleteSql = "DELETE FROM reservation"
                + " WHERE id = ?";

        int updatedRows = jdbcTemplate.update(deleteSql, id);
        if (updatedRows < 1) {
            throw new EntityNotFoundException("존재하지 않는 예약 id입니다.");
        }
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> {
            long timeId = resultSet.getLong("time_id");
            LocalTime startAt = resultSet.getObject("start_at", LocalTime.class);

            return Reservation.retrieve(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getObject("date", LocalDate.class),
                    ReservationTime.retrieve(timeId, startAt)
            );
        };
    }
}

package roomescape.persist.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.persist.entity.ReservationEntity;
import roomescape.persist.entity.ReservationTimeEntity;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationTime;

@Repository
public class H2ReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public H2ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
                SELECT
                    r.id AS reservation_id,
                    r.name,
                    r.date,
                    t.id AS time_id,
                    t.start_at AS time_value
                FROM reservation r
                INNER JOIN reservation_time t
                    ON r.time_id = t.id""";
        List<ReservationEntity> reservationEntities = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new ReservationEntity(
                        resultSet.getLong("reservation_id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        new ReservationTimeEntity(
                                resultSet.getLong("time_id"),
                                resultSet.getString("time_value")
                        )
                )
        );
        return reservationEntities.stream()
                .map(reservationEntity -> new Reservation(
                        reservationEntity.getId(),
                        reservationEntity.getName(),
                        new ReservationDate(LocalDate.parse(reservationEntity.getDate(),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
                        new ReservationTime(reservationEntity.getTimeEntity().getId(),
                                LocalTime.parse(reservationEntity.getTimeEntity().getStartAt(),
                                        DateTimeFormatter.ofPattern("HH:mm")))))
                .toList();
    }

    @Override
    public Reservation add(Reservation reservation) {
        ReservationEntity reservationEntity = new ReservationEntity(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().getStartDate().toString(),
                new ReservationTimeEntity(reservation.getTime().getId(),
                        reservation.getTime().getStartTime().toString()));
        Map<String, String> params = new HashMap<>();
        params.put("name", reservationEntity.getName());
        params.put("date", reservationEntity.getDate());
        params.put("time_id", reservationEntity.getTimeEntity().getId().toString());
        long id = jdbcInsert.executeAndReturnKey(params).intValue();
        return new Reservation(id, reservation.getName(), reservation.getDate().getStartDate(),
                reservation.getTime().getStartTime());
    }

    @Override
    public void removeById(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.ReservationEntity;
import roomescape.model.Reservation;

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
        String sql = "SELECT id, name, date, time FROM Reservation";
        List<ReservationEntity> reservationEntities = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new ReservationEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("time")
                )
        );
        return reservationEntities.stream()
                .map(reservationEntity -> new Reservation(
                        reservationEntity.getId(),
                        reservationEntity.getName(),
                        LocalDate.parse(reservationEntity.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        LocalTime.parse(reservationEntity.getTime(), DateTimeFormatter.ofPattern("HH:mm"))))
                .toList();
    }

    @Override
    public Reservation add(Reservation reservation) {
        ReservationEntity reservationEntity = new ReservationEntity(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate().getStartDate().toString(),
                reservation.getTime().getStartTime().toString());
        Map<String, String> params = new HashMap<>();
        params.put("name", reservationEntity.getName());
        params.put("date", reservationEntity.getDate());
        params.put("time", reservationEntity.getTime());
        long id = jdbcInsert.executeAndReturnKey(params).intValue();
        return new Reservation(id, reservation.getName(), reservation.getDate().getStartDate(), reservation.getTime().getStartTime());
    }

    @Override
    public void removeById(long id) {
        String sql = "DELETE FROM Reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

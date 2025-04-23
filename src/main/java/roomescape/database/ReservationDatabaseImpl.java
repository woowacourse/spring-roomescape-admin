package roomescape.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationDatabaseImpl implements ReservationDatabase {

    private final List<Reservation> reservations = new ArrayList<>();

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "select id, name, date, time from reservation",
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getDate("date").toLocalDate(),
                            resultSet.getTime("time").toLocalTime()
                    );
                    return reservation;
                });
    }

    @Override
    public Reservation findById(Long id) {
        return reservations.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 예약 번호입니다:" + id));
    }

    @Override
    public Reservation add(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, reservation.getName(), reservation.getDate(), reservation.getTime());
        return reservation;
    }

    @Override
    public void delete(Reservation reservation) {
        reservations.remove(reservation);
    }
}

package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import roomescape.domain.Reservation;

public class ReservationRepository {

    private static final RowMapper<Reservation> reservationRowMapper;

    @Autowired
    private final JdbcTemplate template;

    static {
        reservationRowMapper = (resultSet, resultNumber) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime());
    }

    public ReservationRepository(JdbcTemplate template) {
        this.template = template;
    }

    public List<Reservation> findAll() {
        return template.query(
                "SELECT * FROM reservation",
                (resultSet, resultNumber) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("time").toLocalTime())
        );
    }

    public Optional<Reservation> findById(long id) {
        String sql = "SELECT * FROM reservation WHERE ?";
        try {
            Reservation reservation = template.queryForObject(sql, reservationRowMapper, id);
            return Optional.of(reservation);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public Reservation add(String name, LocalDate date, LocalTime time) {
        return new Reservation(1L, name, date, time);
    }

    public void deleteById(long id) {
        if (findById(id).isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 해당 id의 예약이 없습니다: " + id);
        }
    }
}

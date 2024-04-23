package roomescape.repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
@Primary
public class ReservationH2Repository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationH2Repository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation add(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                    new String[]{"id"}
            );
            ps.setString(1, reservation.name());
            ps.setString(2, reservation.date().format(DateTimeFormatter.ISO_DATE));
            ps.setString(3, reservation.time().format(DateTimeFormatter.ofPattern("HH:mm")));
            return ps;
        }, keyHolder);

        return Reservation.of(keyHolder.getKey().longValue(), reservation);
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update("DELETE FROM RESERVATION WHERE id = ?", id);
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM reservation",
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        LocalDate.parse(resultSet.getString("date")),
                        LocalTime.parse(resultSet.getString("time"))
                )
        );
    }
}

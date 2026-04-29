package roomescape.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.mapper.ReservationMapper;
import roomescape.util.DateAndTimeConverter;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public Long save(Reservation reservation) {
        String insertSql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql, new String[]{"id"});

            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setString(2, DateAndTimeConverter.formatDate(reservation.getDate()));
            preparedStatement.setString(3, DateAndTimeConverter.formatTime(reservation.getTime()));

            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKeyAs(Long.class);
    }

    public Long getId(Reservation reservation) {
        String selectSql = "SELECT id FROM reservation WHERE name = ? " +
                "AND date = ? AND time = ?";

        return jdbcTemplate.queryForObject(selectSql, Long.class,
                reservation.getName(), reservation.getDate(), reservation.getTime()
        );
    }

    public List<Reservation> getAll() {
        String selectAllSql = "SELECT id, name, date, time FROM reservation";

        return jdbcTemplate.query(selectAllSql, new ReservationMapper());
    }

    public void deleteById(Long id) {
        String deleteSql = "DELETE FROM reservation WHERE id = ?";

        jdbcTemplate.update(deleteSql, id);
    }
}

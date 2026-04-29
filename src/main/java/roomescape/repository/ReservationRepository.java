package roomescape.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.util.DateAndTimeConverter;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationRowMapper =
            (resultSet, rowNumber) -> Reservation.create(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    DateAndTimeConverter.parseToDate(resultSet.getString("date")),
                    DateAndTimeConverter.parseToTime(resultSet.getString("startAt"))
            );

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

    public List<Reservation> getAll() {
        String selectAllSql = "SELECT id, name, date, time FROM reservation";

        return jdbcTemplate.query(selectAllSql, reservationRowMapper);
    }

    public void deleteById(Long id) {
        String deleteSql = "DELETE FROM reservation WHERE id = ?";

        jdbcTemplate.update(deleteSql, id);
    }
}

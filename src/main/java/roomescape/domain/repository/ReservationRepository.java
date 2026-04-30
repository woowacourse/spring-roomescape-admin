package roomescape.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.entity.Reservation;
import roomescape.domain.entity.ReservationTime;
import roomescape.util.DateAndTimeConverter;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationRowMapper =
            (resultSet, rowNumber) -> {
                ReservationTime reservationTime = ReservationTime.create(
                        resultSet.getLong("time_id"),
                        DateAndTimeConverter.parseToTime(resultSet.getString("time_value"))
                );

                return Reservation.create(
                        resultSet.getLong("reservation_id"),
                        resultSet.getString("name"),
                        DateAndTimeConverter.parseToDate(resultSet.getString("date")),
                        reservationTime);
            };

    public Long save(Reservation reservation) {
        String insertSql = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql, new String[]{"id"});

            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setString(2, DateAndTimeConverter.formatDate(reservation.getDate()));
            preparedStatement.setLong(3, reservation.getTime().getId());

            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKeyAs(Long.class);
    }

    public List<Reservation> getAll() {
        String selectAllSql = "SELECT r.id as reservation_id, r.name, r.date, " +
                "t.id as time_id, t.start_at as time_value " +
                "FROM reservation as r " +
                "INNER JOIN reservation_time as t " +
                "ON r.time_id = t.id";

        return jdbcTemplate.query(selectAllSql, reservationRowMapper);
    }

    public void deleteById(Long id) {
        String deleteSql = "DELETE FROM reservation WHERE id = ?";

        jdbcTemplate.update(deleteSql, id);
    }
}

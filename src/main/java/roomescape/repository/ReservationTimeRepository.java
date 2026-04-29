package roomescape.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.util.DateAndTimeConverter;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ReservationTime> reservationTimeRowMapper =
            (resultSet, rowNumber) -> ReservationTime.create(
                    resultSet.getLong("id"),
                    DateAndTimeConverter.parseToLocalDateTime(resultSet.getString("time"))
            );

    public Long save(ReservationTime reservationTime) {
        String insertSql = "INSERT INTO reservation_time(start_at) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, DateAndTimeConverter.formatDateAndTime(reservationTime.getStartAt()));

            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKeyAs(Long.class);
    }

    public List<ReservationTime> getAll() {
        String selectAllSql = "SELECT id, time FROM reservation_time";

        return jdbcTemplate.query(selectAllSql, reservationTimeRowMapper);
    }

    public void delete(Long id) {
        String deleteSql = "DELETE FROM reservation_time WHERE id = ?";

        jdbcTemplate.update(deleteSql, id);
    }
}


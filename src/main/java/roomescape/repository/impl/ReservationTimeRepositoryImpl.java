package roomescape.repository.impl;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Repository
public class ReservationTimeRepositoryImpl implements ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationTimeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime createReservationTime(ReservationTime reservationTime) {
        final String query = "INSERT INTO reservation_time (start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setObject(1, reservationTime.getStartAt());
            return ps;
        }, keyHolder);

        ReservationTime createdReservationTime = ReservationTime.generateWithPrimaryKey(
                reservationTime, Objects.requireNonNull(keyHolder.getKey()).longValue());

        return createdReservationTime;
    }

    public List<ReservationTime> readReservationTimes() {
        final String query = "SELECT id, start_at FROM reservation_time";
        List<ReservationTime> reservationTimes = jdbcTemplate.query(
                query,
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getLong("id"),
                        resultSet.getTime("start_at").toLocalTime()
                )
        );

        return reservationTimes;
    }

    public Optional<ReservationTime> readReservationTime(Long timeId) {
        final String query = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(query, (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getLong("id"),
                        resultSet.getTime("start_at").toLocalTime()
                ), timeId)
        );
    }

    public void deleteReservationTime(Long id) {
        final String query = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(query, id);
    }
}

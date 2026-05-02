package roomescape.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long create(ReservationTime reservationTime) {
        String sql = "INSERT INTO reservation_time(start_at) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                    ps.setString(1, reservationTime.getStartAt());
                    return ps;
                }, keyHolder
        );

        return keyHolder.getKey().longValue();
    }

    public List<ReservationTime> getTimes() {
        String sql = "SELECT * FROM reservation_time";

        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> {
                    ReservationTime reservationTime = new ReservationTime(
                            resultSet.getLong("id"),
                            resultSet.getString("start_at")
                    );
                    return reservationTime;
                });
    }

    public void delete(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";

        try {
            int rowAffected = jdbcTemplate.update(sql, id);
            if (rowAffected == 0) {
                throw new NoSuchElementException();
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("해당 시간에 예약이 있어 삭제할 수 없습니다.");
        }
    }

    public Optional<ReservationTime> findById(long id) {
        String sql = "SELECT * FROM reservation_time WHERE id = ?";

        try {
            ReservationTime reservationTime = jdbcTemplate.queryForObject(sql,
                    (resultSet, rowNum) -> {
                        ReservationTime time = new ReservationTime(
                                resultSet.getLong("id"),
                                resultSet.getString("start_at")
                        );

                        return time;
                    }, id);
            return Optional.ofNullable(reservationTime);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}

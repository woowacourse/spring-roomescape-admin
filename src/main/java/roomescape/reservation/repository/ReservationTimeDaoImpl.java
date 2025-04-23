package roomescape.reservation.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.entity.ReservationTime;
import roomescape.reservation.exception.EntityNotFoundException;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationTimeDaoImpl implements ReservationTimeDao{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ReservationTimeDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (:startAt)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, new MapSqlParameterSource("startAt", reservationTime.getStartAt()), keyHolder);

        Number key = keyHolder.getKey();
        return new ReservationTime(key.longValue(), reservationTime.getStartAt());
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM reservation_time";

        return jdbcTemplate.query(sql, getReservationTimeRowMapper());
    }

    @Override
    public void deleteById(Long id) {
        if (!existReservationTime(id)) {
            throw new EntityNotFoundException("삭제할 예약시간이 없습니다.");
        }
        String sql = "DELETE FROM reservation_time WHERE id = :id";
        jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        String sql = "SELECT * FROM reservation_time WHERE id = :id";

        ReservationTime reservationTime = jdbcTemplate.queryForObject(
            sql, new MapSqlParameterSource("id", id), getReservationTimeRowMapper());
        return Optional.ofNullable(reservationTime);
    }

    private boolean existReservationTime(Long id) {
        String sql = "SELECT EXISTS (SELECT 1 FROM reservation_time WHERE id = :id)";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("id", id), Boolean.class));
    }

    private RowMapper<ReservationTime> getReservationTimeRowMapper() {
        return (resultSet, rowNum) -> new ReservationTime(
            resultSet.getLong("id"),
            resultSet.getObject("start_at", LocalTime.class)
        );
    }
}

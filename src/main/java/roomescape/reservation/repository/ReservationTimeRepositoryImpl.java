package roomescape.reservation.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.common.exception.AlreadyInUseException;
import roomescape.common.exception.EntityNotFoundException;
import roomescape.reservation.entity.ReservationTime;

@Repository
public class ReservationTimeRepositoryImpl implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "select * from reservation_time";

        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> getReservationTime(resultSet)
        );
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        String sql = "select * from reservation_time where id = ?";

        try {
            ReservationTime reservationTime = jdbcTemplate.queryForObject(sql,
                    (resultSet, rowNum) -> getReservationTime(resultSet),
                    id
            );

            return Optional.ofNullable(reservationTime);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("ReservationTime with id " + id + " not found");
        }
    }

    private ReservationTime getReservationTime(ResultSet resultSet) throws SQLException {
        return new ReservationTime(
                resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("start_at"))
        );
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        if (reservationTime.getStartAt() == null) {
            throw new IllegalArgumentException("start_at cannot be null");
        }

        if (reservationTime.existId()) {
            return update(reservationTime);
        }

        return create(reservationTime);
    }

    private ReservationTime update(ReservationTime reservationTime) {
        String sql = "update reservation_time set start_at = ? where id = ?";

        int update = jdbcTemplate.update(sql, reservationTime.getStartAt(), reservationTime.getId());

        if (update == 0) {
            throw new EntityNotFoundException("ReservationTime with id " + reservationTime.getId() + " not found");
        }

        return reservationTime;
    }

    private ReservationTime create(ReservationTime reservationTime) {
        String sql = "insert into reservation_time (start_at) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservationTime.getStartAt().toString());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();

        return new ReservationTime(id, reservationTime.getStartAt());
    }

    @Override
    public void deleteById(Long id) {
        checkUsingReservationTime(id);

        String deleteSql = "delete from reservation_time where id = ?";

        int update = jdbcTemplate.update(deleteSql, id);

        if (update != 1) {
            throw new EntityNotFoundException("ReservationTime with id " + id + " not found");
        }
    }

    private void checkUsingReservationTime(Long timeId) {
        String selectSql = "select count(*) from reservation where time_id = ?";

        Integer count = jdbcTemplate.queryForObject(selectSql, Integer.class, timeId);

        if (count > 0) {
            throw new AlreadyInUseException("reservation time with id " + timeId + " already exists");
        }
    }

}

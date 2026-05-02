package roomescape.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.entity.ReservationTime;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ReservationTime> reservationTimeRowMapper =
            (resultSet, rowNumber) -> ReservationTime.create(
                    resultSet.getLong("id"),
                    LocalTime.parse(resultSet.getString("start_at"))
            );

    public Long save(ReservationTime reservationTime) {
        String insertSql = "INSERT INTO reservation_time(start_at) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql, new String[]{"id"});
            preparedStatement.setString(1, String.valueOf(reservationTime.getStartAt()));

            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKeyAs(Long.class);
    }

    public ReservationTime getById(Long id) {
        String selectSql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(selectSql, reservationTimeRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("존재하지 않는 시간대입니다.");
        }
    }

    public List<ReservationTime> findAll() {
        String selectAllSql = "SELECT id, start_at FROM reservation_time";

        return jdbcTemplate.query(selectAllSql, reservationTimeRowMapper);
    }

    public void deleteById(Long id) {
        String deleteSql = "DELETE FROM reservation_time WHERE id = ?";

        int executeCount = jdbcTemplate.update(deleteSql, id);
        if (executeCount == 0) {
            throw new IllegalArgumentException("존재하지 않는 시간대입니다.");
        }
    }

    public boolean existsByDateAndTime(LocalTime time) {
        String sql = "SELECT EXISTS (" +
                "SELECT 1 FROM reservation_time WHERE start_at = ?" +
                ")";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                time
        ));
    }
}


package roomescape.reservationtime.repository;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservationtime.domain.ReservationTime;

@Repository
public class ReservationTimeRepositoryImpl implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "select id, start_at from reservation_time";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getLong("id"),
                        LocalTime.parse(resultSet.getString("start_at"))
                )
        );
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        String sql = "select id, start_at from reservation_time where id = ?";
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                        sql,
                        (resultSet, rowNum) -> new ReservationTime(
                                resultSet.getLong("id"),
                                LocalTime.parse(resultSet.getString("start_at"))
                        ),
                        id
                ));
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        String sql = "insert into reservation_time (start_at) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"}
            );
            ps.setString(1, reservationTime.getStartAt().toString());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();

        return new ReservationTime(id, reservationTime.getStartAt());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }
}

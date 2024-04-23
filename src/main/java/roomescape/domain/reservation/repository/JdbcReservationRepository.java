package roomescape.domain.reservation.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.time.ReservationTime;

@Repository // todo DAO 분리
public class JdbcReservationRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsertOperations jdbcInsert;
    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getDate("date").toLocalDate(),
            new ReservationTime(rs.getLong("time_id"), rs.getTime("start_at").toLocalTime())
    );

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Reservation save(Reservation reservation) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time_id", reservation.getTimeId());
        long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return reservation.updateId(id);
    }

    @Override
    public boolean existsByReservationDateTime(LocalDate date, long timeId) {
        String sql = "select count(*) from reservation where date = ? and time_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, date, timeId);
        return count != null && count > 0;
    }

    @Override
    public Optional<Reservation> findById(long id) {
        String sql = "select * from reservation as r join reservation_time as t on r.time_id = t.id where r.id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select * from reservation as r join reservation_time as t on r.time_id = t.id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void deleteById(long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}

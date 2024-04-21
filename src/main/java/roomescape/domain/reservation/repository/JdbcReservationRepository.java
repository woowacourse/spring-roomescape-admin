package roomescape.domain.reservation.repository;

import java.time.Clock;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationDateTime;

@Primary
@Repository // todo DAO 분리
public class JdbcReservationRepository implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsertOperations jdbcInsert;
    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            new ReservationDateTime(
                    rs.getDate("reservation_date_time").toLocalDate(),
                    rs.getTime("reservation_date_time").toLocalTime(),
                    Clock.systemUTC() // todo 삭제
            )
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
                .addValue("reservation_date_time", reservation.getReservationDateTime());
        long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return reservation.updateId(id);
    }

    @Override
    public boolean existsByReservationDateTime(ReservationDateTime reservationDateTime) {
        String sql = "select count(*) from reservation where reservation_date_time = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, reservationDateTime.getDateTime());
        return count != null && count > 0;
    }

    @Override
    public Optional<Reservation> findById(long id) {
        String sql = "select * from reservation where id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select * from reservation";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void deleteById(long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}

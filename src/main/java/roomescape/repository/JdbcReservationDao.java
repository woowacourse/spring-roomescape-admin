package roomescape.repository;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcReservationDao implements ReservationRepository {

    private static final RowMapper<Reservation> rowMapper = (rs, rowNum) -> {
        String date = rs.getString("date");
        Long timeId = rs.getLong("time_id");
        String timeValue = rs.getString("start_at");
        ReservationTime reservationTime = new ReservationTime(timeId, LocalTime.parse(timeValue));

        Reservation reservation = new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                LocalDate.parse(date),
                reservationTime
        );
        return reservation;
    };

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcReservationDao(final JdbcTemplate jdbcTemplate, DataSource source) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(source)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
                SELECT *
                FROM reservation as r
                inner join reservation_time as t
                on r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Reservation save(final Reservation reservation) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("name", reservation.name());
            params.put("date", reservation.date());
            params.put("time_id", reservation.time().id());

            Long id = jdbcInsert.executeAndReturnKey(params).longValue();
            return findById(id);
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("[ERROR] 이미 등록된 예약 입니다.");
        }
    }

    private Reservation findById(Long id) {
        String sql = """
                SELECT *
                FROM reservation as r
                inner join reservation_time as t
                on r.time_id = t.id
                where r.id = ?
                """;
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public int deleteById(final long id) {
        String sql = "delete from reservation where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

package roomescape.domain.reservation;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private static final String INSERT_SQL =
        "insert into reservation(name, date, time) values (?, ?, ?)";
    private static final String FIND_ALL_SQL =
        "select id, name, date, time from reservation order by id";
    private static final String FIND_BY_ID_SQL =
        "select id, name, date, time from reservation where id = ?";
    private static final String DELETE_BY_ID_SQL =
        "delete from reservation where id = ?";

    private final JdbcTemplate jdbcTemplate;

    public Reservation save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setString(3, reservation.getTime().toString());
            return ps;
        }, keyHolder);
        long id = extractId(keyHolder);
        return Reservation.createWithId(id, reservation);
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, reservationRowMapper());
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update(DELETE_BY_ID_SQL, id);
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (rs, rowNum) -> Reservation.of(
            rs.getLong("id"),
            rs.getString("name"),
            LocalDate.parse(rs.getString("date")),
            LocalTime.parse(rs.getString("time"))
        );
    }

    private long extractId(KeyHolder keyHolder) {
        if (keyHolder.getKey() == null) {
            throw new IllegalStateException("생성 키를 조회할 수 없습니다.");
        }
        return keyHolder.getKey().longValue();
    }
}

package roomescape.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@Repository
public class JdbcReservationRepository implements ReservationRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final static RowMapper<Reservation> RESERVATION_ROW_MAPPER =
            (rs, rowNum) -> {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                LocalDate date = rs.getObject("date", LocalDate.class);
                Long time_id = rs.getLong("time_id");
                LocalTime time = rs.getObject("time_value", LocalTime.class);

                return Reservation.of(id, name, date, ReservationTime.of(time_id, time));
            };

    @Override
    public List<Reservation> findAll() {
        String findAllSql = """
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                FROM reservation as r
                inner join reservation_time as t
                on r.time_id = t.id
                """;
        return jdbcTemplate.query(findAllSql, RESERVATION_ROW_MAPPER);
    }

    @Override
    public Long save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String insertSql = "INSERT INTO reservation(name, date, time_id) VALUES(?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    insertSql,
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, reservation.getName());
            ps.setDate(2, Date.valueOf(reservation.getReservationDate()));
            ps.setLong(3, reservation.getReservationTime().getId());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("예약 저장 중 id 생성 실패");
        }
        return key.longValue();
    }

    @Override
    public void deleteById(Long id) {
        String deleteSql = "DELETE FROM reservation WHERE id=?";
        jdbcTemplate.update(deleteSql, id);
    }
}

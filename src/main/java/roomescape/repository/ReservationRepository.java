package roomescape.repository;

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

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper =
            (rs, rowNum) -> {
                LocalDate date = rs.getObject("reservation_date", LocalDate.class);
                LocalTime time = rs.getObject("reservation_time", LocalTime.class);
                return Reservation.of(
                        rs.getLong("id"),
                        rs.getString("name"),
                        date,
                        time
                );
            };

    public List<Reservation> findAll() {
        String findAllSql = "SELECT id, name, reservation_date, reservation_time FROM reservation";
        return jdbcTemplate.query(findAllSql, reservationRowMapper);
    }

    public Long save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String insertSql = "INSERT INTO reservation(name, reservation_date, reservation_time) VALUES(?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    insertSql,
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, reservation.getName());
            ps.setObject(2, reservation.getReservationTime().toLocalDate());
            ps.setObject(3, reservation.getReservationTime().toLocalTime());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("예약 저장 중 id 생성 실패");
        }
        return key.longValue();
    }

    public void deleteById(Long id) {
        String deleteSql = "DELETE FROM reservation WHERE id=?";
        jdbcTemplate.update(deleteSql, id);
    }

}

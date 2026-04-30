package roomescape.persistence.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequestDto;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
        Time time = new Time(resultSet.getLong("time_id"),
                LocalTime.parse(resultSet.getString("start_at")));

        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
              time
        );
        return reservation;
    };

    public List<Reservation> findAll() {
        String sql = """
                SELECT 
                    r.id,
                    r.name,
                    r.date,
                    t.id AS time_id,
                    t.start_at
                    FROM reservation r
                INNER JOIN reservation_time t ON r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Reservation findById(Long id){
        String sql = """
                SELECT
                    r.id,
                    r.name,
                    r.date,
                    t.id AS time_id,
                    t.start_at
                FROM reservation r
                INNER JOIN reservation_time t ON r.time_id = t.id
                WHERE r.id = ?
                """;
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }


    public Long insert(ReservationRequestDto reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = """
                INSERT INTO reservation
                (name, date, time_id)
                VALUES (?, ?, ?)
                """;
        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql, new String[]{"id"});
            pstmt.setString(1, reservation.getName());
            pstmt.setString(2, reservation.getDate().toString());
            pstmt.setLong(3, reservation.getTimeId());
            return pstmt;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public int delete(Long id) {
        String sql = """
                DELETE FROM reservation
                WHERE id = ?
                """;
        return jdbcTemplate.update(sql, id);
    }
}

package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationDao {
    private static final String FAILED_ID_GENERATE = "ID 생성에 실패하였습니다.";

    private static final RowMapper<Reservation> MAPPER = (rs, rowNumber) -> new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("date"),
            rs.getString("time")
    );

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Reservation> getAllReservation() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, MAPPER);
    }

    public Reservation insertReservation(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[] { "id" });
            statement.setString(1, reservation.name());
            statement.setString(2, reservation.date());
            statement.setString(3, reservation.time());
            return statement;
        }, keyHolder);

        Number key = keyHolder.getKey();

        if(key == null) {
            throw new RuntimeException(FAILED_ID_GENERATE);
        }
        return reservation.update(key.longValue());
    }

    public int deleteReservation(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

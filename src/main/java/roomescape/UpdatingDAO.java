package roomescape;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UpdatingDAO {

    private final JdbcTemplate jdbcTemplate;

    public UpdatingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long add(Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setString(3, reservation.getTime().toString());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void delete(Long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}

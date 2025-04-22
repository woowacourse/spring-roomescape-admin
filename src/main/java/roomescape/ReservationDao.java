package roomescape;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Reservation> allRowMapper = (rs, rowNum) -> new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getDate("date").toLocalDate(),
            rs.getTime("time").toLocalTime()
    );

    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, allRowMapper);
    }

    public Optional<Reservation> findById(Long id) {
        String sql = "SELECT id, name, date, time FROM reservation WHERE id = ?";
        try {
            Reservation found = jdbcTemplate.queryForObject(sql, allRowMapper, id);
            return Optional.ofNullable(found);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";
        PreparedStatementCreator ppsc = con -> {
            PreparedStatement ppst = con.prepareStatement(
                    sql, new String[]{"id"}
            );
            ppst.setString(1, reservation.getName());
            ppst.setString(2, reservation.getDate().toString());
            ppst.setString(3, reservation.getTime().toString());
            return ppst;
        };
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(ppsc, keyHolder);
        Long id = keyHolder.getKey().longValue();
        return new Reservation(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public int delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

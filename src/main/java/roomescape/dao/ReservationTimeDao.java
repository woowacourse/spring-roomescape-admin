package roomescape.dao;

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
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<ReservationTime> reservationTimeRowMapper = (rs, rowNum) -> new ReservationTime(
            rs.getLong("id"),
            rs.getTime("start_at").toLocalTime()
    );

    public ReservationTime save(ReservationTime reservationTime) {
        String sql = "INSERT INTO RESERVATION_TIME(start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = (con) -> {
            PreparedStatement ppst = con.prepareStatement(sql, new String[]{"id"});
            ppst.setString(1, reservationTime.getStartAt().toString());
            return ppst;
        };
        jdbcTemplate.update(psc, keyHolder);
        return new ReservationTime(
                keyHolder.getKey().longValue(),
                reservationTime.getStartAt()
        );
    }

    public Optional<ReservationTime> findById(Long id) {
        String sql = "SELECT * FROM RESERVATION_TIME WHERE id = ?";
        try {
            ReservationTime reservationTime = jdbcTemplate.queryForObject(sql, reservationTimeRowMapper, id);
            return Optional.ofNullable(reservationTime);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM RESERVATION_TIME";
        return jdbcTemplate.query(sql, reservationTimeRowMapper);
    }

    public int delete(Long id) {
        String sql = "DELETE FROM RESERVATION_TIME WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}

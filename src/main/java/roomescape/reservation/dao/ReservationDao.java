package roomescape.reservation.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationTime;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationDao(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation insert(final Reservation reservation) {
        String sql = "INSERT INTO reservation (name,date,time_id) VALUES (?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        long id = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setObject(2, reservation.getDate());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        return new Reservation(id, reservation);
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT * FROM reservation as r JOIN reservation_time rt ON r.time_id = rt.id",
                ROW_MAPPER);
    }

    public int deleteById(final Long id) {
        String sql = "DELETE FROM reservation WHERE id = (?)";
        int updateCount = jdbcTemplate.update(sql, id);

        return updateCount;
    }

    private static final RowMapper<Reservation> ROW_MAPPER = (rs, rowNum) -> {
        Reservation reservation = new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getObject("date", LocalDate.class),
                new ReservationTime(rs.getLong("time_id"), rs.getObject("start_at", LocalTime.class))
        );

        return reservation;
    };
}

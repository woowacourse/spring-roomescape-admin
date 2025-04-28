package roomescape.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationJdbcDao implements ReservationDao {

    private static final String SELECT_RESERVATION_WITH_TIME =
            "SELECT r.id, r.name, r.date, rt.id as time_id, rt.start_at as time_start_at FROM reservation as r "
                    + " inner join reservation_time as rt"
                    + " on r.time_id = rt.id";

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (rs, rowNum) ->
            new Reservation(rs.getLong("id"),
                    rs.getString("name"),
                    rs.getDate("date").toLocalDate(),
                    new ReservationTime(
                            rs.getLong("time_id"),
                            rs.getTime("time_start_at").toLocalTime()
                    )
            );

    private final JdbcTemplate jdbcTemplate;

    public ReservationJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(final Reservation reservation) {
        String query = "INSERT INTO reservation (name, date, time_id) "
                + " VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setDate(2, Date.valueOf(reservation.getDate()));
            ps.setLong(3, reservation.getReservationTime().getId());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        reservation.setId(id);

        return id;
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(SELECT_RESERVATION_WITH_TIME, RESERVATION_ROW_MAPPER);
    }

    @Override
    public void deleteById(final Long id) {
        String query = "DELETE FROM reservation "
                + " WHERE id = ?";

        jdbcTemplate.update(query, id);
    }

    @Override
    public Optional<Reservation> findById(final Long id) {
        String query = SELECT_RESERVATION_WITH_TIME
                + " WHERE r.id = ?";

        return jdbcTemplate.query(query, RESERVATION_ROW_MAPPER, id).stream().findAny();
    }
}

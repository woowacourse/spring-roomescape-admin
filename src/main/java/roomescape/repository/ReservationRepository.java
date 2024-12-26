package roomescape.repository;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationRepository {

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (rs, rowNum) -> {
        return new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                new ReservationTime(
                        rs.getLong("time_id"),
                        rs.getTime("start_at").toLocalTime()
                )
        );
    };

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .usingGeneratedKeyColumns("id")
                .withTableName("reservation");
    }

    public Reservation save(Reservation reservation) {
        Long timeId = reservation.getTime().getId();
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time_id", timeId);
        Number key = jdbcInsert.executeAndReturnKey(params);
        return new Reservation(key.longValue(), reservation);
    }

    public Optional<Reservation> findById(Long id) {
        String sql = "select r.id, r.name, r.date, r.time_id, rt.start_at "
                + "from reservation as r "
                + "left join reservation_time as rt on rt.id = r.time_id "
                + "where r.id = ?";
        try {
            Reservation reservation = jdbcTemplate.queryForObject(sql, RESERVATION_ROW_MAPPER, id);
            return Optional.of(reservation);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Reservation> findAll() {
        String sql = "select r.id, r.name, r.date, r.time_id, rt.start_at "
                + "from reservation as r "
                + "left join reservation_time as rt on rt.id = r.time_id";
        return jdbcTemplate.query(sql, RESERVATION_ROW_MAPPER);
    }

    public void delete(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean existsByTimeId(Long timeId) {
        String sql = "select count(1) from reservation where time_id = ?";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, timeId);

        return count > 0;
    }
}

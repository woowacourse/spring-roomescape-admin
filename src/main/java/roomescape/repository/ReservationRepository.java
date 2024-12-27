package roomescape.repository;

import java.time.LocalDate;
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
import roomescape.domain.Theme;

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
                ),
                new Theme(
                        rs.getLong("theme_id"),
                        rs.getString("theme_name"),
                        rs.getString("description"),
                        rs.getString("thumbnail")
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
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time_id", reservation.getTime().getId())
                .addValue("theme_id", reservation.getTheme().getId());

        Number key = jdbcInsert.executeAndReturnKey(params);
        return new Reservation(key.longValue(), reservation);
    }

    public Optional<Reservation> findById(Long id) {
        String sql =
                "select r.id, r.name, r.date, r.time_id, rt.start_at, r.theme_id, t.name as theme_name, t.description, t.thumbnail "
                        + "from reservation as r "
                        + "left join reservation_time as rt on rt.id = r.time_id "
                        + "left join theme as t on t.id = r.theme_id "
                        + "where r.id = ?";
        try {
            Reservation reservation = jdbcTemplate.queryForObject(sql, RESERVATION_ROW_MAPPER, id);
            return Optional.of(reservation);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Reservation> findAll() {
        String sql =
                "select r.id, r.name, r.date, r.time_id, rt.start_at, r.theme_id, t.name as theme_name, t.description, t.thumbnail "
                        + "from reservation as r "
                        + "left join reservation_time as rt on rt.id = r.time_id "
                        + "left join theme as t on t.id = r.theme_id ";
        return jdbcTemplate.query(sql, RESERVATION_ROW_MAPPER);
    }

    public List<Long> findTimeIdByDateAndThemeId(LocalDate date, Long themeId) {
        String sql = """
                select time_id
                from reservation
                where date = ? and theme_id = ?
                """;
        return jdbcTemplate.queryForList(sql, Long.class, date, themeId);
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

    public boolean existsByThemeId(Long themeId) {
        String sql = "select count(1) from reservation where theme_id = ?";
        long count = jdbcTemplate.queryForObject(sql, Long.class, themeId);

        return count > 0;
    }
}

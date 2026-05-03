package roomescape.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationDao {

    private final NamedParameterJdbcTemplate template;

    public ReservationDao(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id)" +
                " VALUES (:name, :date, :time_id)";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time_id", reservation.getTime().getId());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return Reservation.withId(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public List<Reservation> findAll() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at FROM reservation as r " +
                "INNER JOIN reservation_time as t ON r.time_id = t.id";
        return template.query(sql, reservationRowMapper());
    }

    public Optional<Reservation> findById(Long id) {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at FROM reservation as r " +
                "INNER JOIN reservation_time as t ON r.time_id = t.id " +
                "WHERE r.id = :id ";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id);

        try {
            return Optional.of(template.queryForObject(sql, param, reservationRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id);
        template.update(sql, param);
    }

    public boolean existsByTimeId(Long timeId) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE time_id = :time_id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("time_id", timeId);
        Integer count = template.queryForObject(sql, param, Integer.class);
        return count != null && count > 0;
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (rs, rowNum) -> Reservation.withId(
                rs.getLong("reservation_id"),
                rs.getString("name"),
                rs.getDate("date").toLocalDate(),
                ReservationTime.withId(
                        rs.getLong("time_id"),
                        rs.getTime("start_at").toLocalTime()
                )
        );
    }
}

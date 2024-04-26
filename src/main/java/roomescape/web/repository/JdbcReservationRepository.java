package roomescape.web.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.core.domain.Reservation;
import roomescape.core.domain.ReservationTime;
import roomescape.core.repository.ReservationRepository;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = getReservationRowMapper();

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    private static RowMapper<Reservation> getReservationRowMapper() {
        return (resultSet, rowNum) -> {
            ReservationTime reservationTime = new ReservationTime(resultSet.getLong("time_id"),
                    LocalTime.parse(resultSet.getString("start_at")));

            return new Reservation(resultSet.getLong("reservation_id"), resultSet.getString("name"),
                    LocalDate.parse(resultSet.getString("date")), reservationTime);
        };
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        String sql = """
                SELECT a.id AS reservation_id, a.name AS name, a.date AS date, t.id AS time_id, t.start_at AS start_at
                FROM reservation AS a
                LEFT JOIN reservation_time AS t ON a.time_id = t.id
                WHERE a.id = ?""";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, RESERVATION_ROW_MAPPER, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
                SELECT a.id AS reservation_id, a.name AS name, a.date AS date, t.id AS time_id, t.start_at AS start_at
                FROM reservation AS a
                LEFT JOIN reservation_time AS t ON a.time_id = t.id""";

        return jdbcTemplate.query(sql, RESERVATION_ROW_MAPPER);
    }

    @Override
    public Reservation save(Reservation reservation) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(reservation);
        Number key = jdbcInsert.executeAndReturnKey(parameterSource);

        return new Reservation(key.longValue(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Override
    public void delete(Reservation reservation) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, reservation.getId());
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM reservation";
        jdbcTemplate.update(sql);
    }

    @Override
    public Boolean existByTimeId(Long timeId) {
        String sql = """
                SELECT id
                FROM reservation
                WHERE time_id = ?
                LIMIT 1""";

        try {
            Long anyId = jdbcTemplate.queryForObject(sql, Long.class, timeId);
            return anyId != null;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}

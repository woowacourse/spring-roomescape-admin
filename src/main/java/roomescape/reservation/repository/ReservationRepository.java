package roomescape.reservation.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = "select * from reservation";
        return jdbcTemplate.query(sql, (resultSet, rowNum) ->
                new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        LocalDateTime.of(
                                LocalDate.parse(resultSet.getString("date")),
                                LocalTime.parse(resultSet.getString("time"))
                        )
                ));
    }

    public Reservation save(Reservation reservation) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", reservation.getReserverName())
                .addValue("date", reservation.getDateTime().toLocalDate())
                .addValue("time", reservation.getDateTime().toLocalTime());
        Long id = jdbcInsert.executeAndReturnKey(parameters).longValue();

        return new Reservation(id, reservation.getReserverName(), reservation.getDateTime());
    }

    public Optional<Reservation> findById(Long id) {
        String sql = "select * from reservation where id = ?";

        Reservation reservation;
        try {
            reservation = jdbcTemplate.queryForObject(sql, (resultSet, rowNum) ->
                    new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            LocalDateTime.of(
                                    LocalDate.parse(resultSet.getString("date")),
                                    LocalTime.parse(resultSet.getString("time"))
                            )
                    ), id);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

        return Optional.of(Objects.requireNonNull(reservation));
    }

    public void deleteById(Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}

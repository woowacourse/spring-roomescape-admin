package roomescape.reservation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.entity.ReservationTime;
import roomescape.reservation.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationInMemoryRepository implements ReservationRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationInMemoryRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT r.id AS resservation_id, r.name, r.date, t.id AS time_id, t.start_at AS time_value FROM reservation r JOIN reservation_time t ON r.id = t.id";
        return jdbcTemplate.query(sql, getReservationRowMapper());
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES(:name, :date, :time_id)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("name", reservation.getName())
            .addValue("date", reservation.getDate())
            .addValue("time_id", reservation.getTime().getId());
        jdbcTemplate.update(sql, mapSqlParameterSource, keyHolder);

        Number key = keyHolder.getKey();
        return new Reservation(key.longValue(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Override
    public void deleteById(Long id) {
        if (!existReservation(id)) {
            throw new EntityNotFoundException("삭제할 예약정보가 없습니다.");
        }
        String sql = "DELETE FROM reservation WHERE id = :id";
        jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }

    private boolean existReservation(Long id) {
        String sql = "SELECT EXISTS (SELECT 1 FROM reservation WHERE id = :id)";
        return Boolean.TRUE.equals(
            jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("id", id), Boolean.class)
        );
    }

    private RowMapper<Reservation> getReservationRowMapper() {
        return (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getObject("date", LocalDate.class),
            new ReservationTime(
                resultSet.getLong("time_id"),
                resultSet.getObject("time_value", LocalTime.class)
            )
        );
    }
}

package roomescape.reservation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.reservation.entity.Reservation;
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
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, getReservationRowMapper());
    }

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES(:name, :date, :time)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("name", reservation.getName())
            .addValue("date", reservation.getDate())
            .addValue("time", reservation.getTime());
        jdbcTemplate.update(sql, mapSqlParameterSource);
        return reservation;
    }

    @Override
    public void deleteById(Long id) {
        if (!existReservation(id)) {
            throw new EntityNotFoundException("삭제할 예약이 없습니다.");
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
            resultSet.getObject("time", LocalTime.class)
        );
    }
}

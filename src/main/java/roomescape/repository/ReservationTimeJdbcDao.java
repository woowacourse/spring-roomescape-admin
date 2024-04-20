package roomescape.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;
import roomescape.repository.dto.ReservationTimeSaveDto;

import java.util.List;
import java.util.Optional;

@Repository
public class ReservationTimeJdbcDao implements ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert reservationTimeInsert;
    private final RowMapper<ReservationTime> reservationTimeConvertor = (selectedTime, rowNum) ->
            new ReservationTime(selectedTime.getLong("id"), selectedTime.getString("start_at"));

    public ReservationTimeJdbcDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationTimeInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_times")
                .usingGeneratedKeyColumns("id");
    }

    public ReservationTime save(final ReservationTimeSaveDto reservationTimeSaveDto) {
        final SqlParameterSource timeParameters = new MapSqlParameterSource()
                .addValue("start_at", reservationTimeSaveDto.startAt());
        final Long savedTimeId = reservationTimeInsert.executeAndReturnKey(timeParameters).longValue();
        return new ReservationTime(savedTimeId, reservationTimeSaveDto.startAt());
    }

    public Optional<ReservationTime> findById(final Long id) {
        final String selectQuery = "SELECT id, start_at FROM reservation_times WHERE id = ?";
        try {
            final ReservationTime time = jdbcTemplate.queryForObject(selectQuery, reservationTimeConvertor, id);
            return Optional.of(time);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public List<ReservationTime> findAll() {
        final String selectQuery = "SELECT id, start_at FROM reservation_times";
        return jdbcTemplate.query(selectQuery, reservationTimeConvertor)
                .stream()
                .toList();
    }

    public boolean deleteById(final Long id) {
        final int affectedRowCount = jdbcTemplate.update("DELETE FROM reservation_times WHERE id = ?", id);
        return affectedRowCount == 1;
    }
}

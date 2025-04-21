package roomescape.repository;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.controller.request.ReservationRequest;
import roomescape.controller.response.ReservationResponse;

@Primary
@Repository
public class H2ReservationRepository implements ReservationRepository {

    private static final RowMapper<ReservationResponse> reservationResponseRowMapper = (resultSet, rowNum) -> {
        return new ReservationResponse(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        );
    };

    private final JdbcTemplate jdbcTemplate;

    public H2ReservationRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationResponse> findAll() {
        String sql = "select id, name, date, time from reservation";
        return jdbcTemplate.query(
                sql,
                reservationResponseRowMapper
        );
    }

    @Override
    public ReservationResponse findById(final Long id) {
        String sql = "select id, name, date, time from reservation where id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, reservationResponseRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public long add(final ReservationRequest request) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        return jdbcTemplate.update(sql, request.name(), request.date(), request.time());
    }

    @Override
    public void deleteById(final Long id) {
        if (findById(id) == null) {
            throw new NoSuchElementException("해당하는 id의 예약기록이 없습니다.");
        }
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }
}

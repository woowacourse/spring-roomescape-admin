package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.domain.Reservation;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationJdbcRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
        final Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
        return reservation;
    };

    public ReservationJdbcRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(final ReservationCreateRequest createRequest) {
        final String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, createRequest.name());
            preparedStatement.setString(2, createRequest.date());
            preparedStatement.setString(3, createRequest.time());
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Reservation> findAll() {
        final String sql = "select * from reservation";
        return jdbcTemplate.query(sql, rowMapper);
    }
}

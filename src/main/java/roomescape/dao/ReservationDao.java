package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = "SELECT id, name, datetime FROM reservation";
        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getObject("datetime", LocalDateTime.class)
                ));
    }

    public Reservation findReservationById(final Long id) {
        String sql = "SELECT id, name, datetime FROM reservation WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getObject("datetime", LocalDateTime.class)
                ), id);
    }

    public Long insertWithKeyHolder(final Reservation reservation) {
        String sql = "INSERT INTO reservation(name, datetime) VALUES(?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, reservation.getCustomerName());
            preparedStatement.setObject(2, reservation.getReservationDateTime());
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }
    
    public boolean deleteById(final Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int deletedRows = jdbcTemplate.update(sql, id);
        return deletedRows > 0;
    }
}

package roomescape.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.entity.ReservationEntity;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationEntity> findAll() {
        final String sql = "SELECT id, name, date, time FROM RESERVATION";
        return jdbcTemplate.query(sql, ReservationEntity.getDefaultRowMapper());
    }
}

package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.entity.ReservationEntity;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        final String sql = "SELECT id, name, date, time FROM RESERVATION";
        return jdbcTemplate.query(sql, ReservationEntity.getDefaultRowMapper()).stream()
                .map(ReservationEntity::toDomain)
                .toList();
    }

    public Long save(final Reservation reservation) {
        final String sql = "INSERT INTO RESERVATION (name, date, time) values (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setString(3, reservation.getTime().toString());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public int remove(final Long id) {
        final String sql = "DELETE FROM RESERVATION WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }
}

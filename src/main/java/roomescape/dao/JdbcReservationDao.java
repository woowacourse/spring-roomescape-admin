package roomescape.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.Reservation;

import java.util.List;

@Repository
@Primary
public class JdbcReservationDao implements ReservationDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation select(Long id) {
        return null;
    }

    @Override
    public List<Reservation> selectAll() {
        return List.of();
    }

    @Override
    public Reservation insert(Reservation reservation) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

}

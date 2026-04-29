package roomescape.domain.reservations.infrastructure;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.reservations.entity.Reservation;

public class ReservationJdbcTemplateRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationJdbcTemplateRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int save(Reservation reservation) {
        return 0;
    }

    @Override
    public Reservation findById(Long id) {
        return null;
    }

    @Override
    public List<Reservation> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(Long id) {

    }
}

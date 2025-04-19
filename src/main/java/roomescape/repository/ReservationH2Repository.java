package roomescape.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class ReservationH2Repository implements ReservationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public long save(final Reservation reservation) {
        return 0L;
    }

    @Override
    public List<Reservation> findAll() {
        String query = "SELECT * FROM reservation";

        return jdbcTemplate.query(query, ((rs, rowNum) ->
                new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime()
                )
        ));
    }

    @Override
    public void deleteById(final long id) {
    }
}

package roomescape.interface_adapter;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.enttity.ReservationTime;
import roomescape.usecase.CreateReservationTimeOutput;
import roomescape.usecase.GetReservationTimeOutput;
import roomescape.usecase.ReservationTimeRepository;

@Repository
public class ReservationTimeRepositoryImpl implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CreateReservationTimeOutput addReservationTime(ReservationTime reservationTime) {
        String sql = "insert into reservation_time(start_at) values(?) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((connection) -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, String.valueOf(reservationTime.getStart_at()));

            return ps;
        }, keyHolder);
        long id = keyHolder.getKey().longValue();
        return new CreateReservationTimeOutput(id, reservationTime.getStart_at());
    }

    @Override
    public GetReservationTimeOutput getReservationTime() {
        return null;
    }
}

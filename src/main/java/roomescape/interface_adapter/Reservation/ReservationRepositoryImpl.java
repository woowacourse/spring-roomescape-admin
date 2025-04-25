package roomescape.interface_adapter.Reservation;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.usecase.Reservation.Reservation;
import roomescape.usecase.Reservation.ReservationRepository;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation addReservation(final Reservation reservation) {
        final String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setString(2, String.valueOf(reservation.getDate()));
            preparedStatement.setString(3, String.valueOf(reservation.getReservationTime().getId()));

            return preparedStatement;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();

        return new Reservation(reservation.getId(), reservation.getName(), reservation.getDate(),
                reservation.getReservationTime());

    }
}

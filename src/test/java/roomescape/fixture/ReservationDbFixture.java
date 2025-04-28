package roomescape.fixture;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationName;
import roomescape.domain.ReservationTime;

public class ReservationDbFixture {
    private final SimpleJdbcInsert reservationInsert;

    public ReservationDbFixture(final JdbcTemplate jdbcTemplate) {
        this.reservationInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation 예약_생성_한스_25_4_22(ReservationTime reservationTime) {
        ReservationName reservationName = ReservationNameFixture.한스;
        LocalDate reservationDate = LocalDate.of(2025, 4, 22);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservationName.getValue());
        parameters.put("date", reservationDate.toString());
        parameters.put("time_id", reservationTime.id());

        Number newId = reservationInsert.executeAndReturnKey(parameters);

        return new Reservation(
                newId.longValue(),
                reservationName,
                new ReservationDate(reservationDate),
                reservationTime
        );
    }
}

package roomescape.fixture;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.dao.ReservationDAO;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

public class ReservationDAOFixture extends ReservationDAO {

    private final List<Reservation> data = new ArrayList<>();
    private final AtomicLong atomicLong = new AtomicLong(1);

    public ReservationDAOFixture(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public List<Reservation> findAll() {
        return data;
    }

    @Override
    public Reservation addAndGet(String name, LocalDate date, long timeId) {
        Reservation newData = new Reservation(atomicLong.getAndIncrement(), name, date, new ReservationTime(timeId, LocalTime.of(10, 0)));
        data.add(newData);
        return newData;
    }

    @Override
    public void deleteById(Long id) {
        Reservation target = data.stream()
                .filter(time -> time.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다."));
        data.remove(target);
    }
}

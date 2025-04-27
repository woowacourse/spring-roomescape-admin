package roomescape.fixture;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.dao.ReservationTimeDAO;
import roomescape.model.ReservationTime;

public class ReservationTimeDAOFixture extends ReservationTimeDAO {

    private final List<ReservationTime> data = new ArrayList<>();
    private final AtomicLong atomicLong = new AtomicLong(1);

    public ReservationTimeDAOFixture(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public List<ReservationTime> findAll() {
        return data;
    }

    @Override
    public ReservationTime addAndGet(LocalTime startAt) {
        ReservationTime newData = new ReservationTime(atomicLong.getAndIncrement(), startAt);
        data.add(newData);
        return newData;
    }

    @Override
    public void deleteById(Long id) {
        ReservationTime target = data.stream()
                .filter(time -> time.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다."));
        data.remove(target);
    }
}

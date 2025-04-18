package roomescape.infra;

import org.springframework.stereotype.Repository;
import roomescape.ReservationTime;
import roomescape.dto.request.ReservationCreateRequest;

import java.util.List;

@Repository
public class ReservationTimeDatabase {

    public List<ReservationTime> findAll() {
        return null;
    }

    public long save(final ReservationCreateRequest request) {
        return 0;
    }

    public void deleteById(final long id) {
    }
}

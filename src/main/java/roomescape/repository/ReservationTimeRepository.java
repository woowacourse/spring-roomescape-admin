package roomescape.repository;

import java.util.List;
import roomescape.controller.request.ReservationTimeRequest;
import roomescape.controller.response.ReservationTimeResponse;

public interface ReservationTimeRepository {

    List<ReservationTimeResponse> findAll();

    ReservationTimeResponse findById(Long id);

    long add(ReservationTimeRequest request);

    void deleteById(Long id);
}

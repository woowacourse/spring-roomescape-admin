package roomescape.reservationTime.repository;

import java.util.List;
import roomescape.reservationTime.controller.request.ReservationTimeRequest;
import roomescape.reservationTime.controller.response.ReservationTimeResponse;

public interface ReservationTimeRepository {

    List<ReservationTimeResponse> findAll();

    ReservationTimeResponse findById(Long id);

    long add(ReservationTimeRequest request);

    void deleteById(Long id);
}

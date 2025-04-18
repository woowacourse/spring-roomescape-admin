package roomescape.service;


import java.util.List;
import roomescape.dto.ReservationReq;
import roomescape.dto.ReservationRes;

public interface ReservationService {

    List<ReservationRes> get();

    ReservationRes create(ReservationReq req);

    void delete(Long id);
}

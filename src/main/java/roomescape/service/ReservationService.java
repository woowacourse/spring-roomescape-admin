package roomescape.service;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationService {

    Reservation save(String name, String date, Long timeId);

    List<Reservation> findAll();

    void deleteById(Long targetId);
}

package roomescape.dao;

import java.util.List;
import roomescape.entity.Reservation;

public interface ReservationDao {

    List<Reservation> findAll();

    Reservation save(Reservation reservation, long timeId);

    void deleteById(long id);
}

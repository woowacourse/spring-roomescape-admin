package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.util.List;

public interface ReservationService {

    Reservation add(ReservationRequest request);

    List<Reservation> findAllReservations();

    void deleteReservation(Long id);
}

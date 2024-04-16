package roomescape.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationResponseDto;
import roomescape.model.Reservation;

@Service
public class ReservationService {

    private final List<Reservation> reservations = new ArrayList<>();

    public List<ReservationResponseDto> getReservations() {
        return reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();
    }
}

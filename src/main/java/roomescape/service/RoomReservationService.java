package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationInfoDto;
import roomescape.dto.ReservationInfosDto;
import roomescape.dto.SaveReservationDto;

@Service
public class RoomReservationService {
    private static final String INVALID_RESERVATION_ID = "해당 예약은 존재하지 않습니다.";

    private List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public ReservationInfosDto getAllReservation() {
        return ReservationInfosDto.from(List.copyOf(reservations));
    }

    public ReservationInfoDto addReservation(SaveReservationDto saveReservationDto) {
        Reservation reservation = new Reservation(index.incrementAndGet(), saveReservationDto.name(), saveReservationDto.date(), saveReservationDto.time());
        reservations.add(reservation);
        return ReservationInfoDto.from(reservation);
    }

    public void deleteReservation(long id) {
       Reservation reservation = reservations.stream()
               .filter(r -> r.id() == id)
               .findFirst()
               .orElseThrow(() -> new NoSuchElementException(INVALID_RESERVATION_ID));

        reservations = reservations.stream()
                .filter(r -> r.equals(reservation))
                .toList();
    }
}

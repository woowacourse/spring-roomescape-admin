package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateReqDto;
import roomescape.dto.ReservationResDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReservationService {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    public ReservationResDto createReservation(ReservationCreateReqDto dto) {
        Reservation reservation = Reservation.of(index.getAndIncrement(), dto.getName(), dto.getDate(), dto.getTime());
        reservations.add(reservation);
        return ReservationResDto.from(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public List<ReservationResDto> getReservations() {
        return reservations.stream()
                .map(r -> ReservationResDto.from(r.getId(), r.getName(), r.getDate(), r.getTime()))
                .toList();
    }

    public ReservationResDto getReservationById(Long id) {
        Reservation reservation = reservations.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));

        return ReservationResDto.from(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public void deleteReservation(Long id) {
        Reservation reservation = reservations.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));

        reservations.remove(reservation);
    }
}

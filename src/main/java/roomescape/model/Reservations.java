package roomescape.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.dto.ReservationReqDto;
import roomescape.dto.ReservationResDto;

public class Reservations {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    public ReservationResDto addAndGet(ReservationReqDto dto) {
        Reservation newReservation = new Reservation(index.incrementAndGet(), dto.name(), dto.date(), dto.time());
        reservations.add(newReservation);
        return convertToDto(newReservation);
    }

    public List<ReservationResDto> getAllReservations() {
        return reservations.stream()
                .map(this::convertToDto)
                .toList();
    }

    private ReservationResDto convertToDto(Reservation reservation) {
        return new ReservationResDto(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public void deleteById(Long id) {
        Reservation selectedReservation = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 예약 id는 존재하지 않습니다."));
        reservations.remove(selectedReservation);
    }
}

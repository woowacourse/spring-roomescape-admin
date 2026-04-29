package roomescape.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Roomescape {

    private final Reservations reservations;
    private final PlayingTime playingTime;

    public Roomescape(Reservations reservations) {
        this.reservations = reservations;
        this.playingTime = PlayingTime.toDefaultPlayingTime();
    }

    public Reservation reserve(String customerName, LocalDateTime startTime) {
        ReservationTime reservationTime = playingTime.calculateReservationTime(startTime);
        if (reservations.hasOverlapTime(reservationTime)) {
            throw new IllegalArgumentException("해당 시간에 이미 예약된 정보가 있습니다.");
        }
        return new Reservation(customerName, reservationTime);
    }

    public void cancelReservation(Long reservationId) {
        List<Reservation> schedule = reservations.getSchedule();
        Reservation toDelete = schedule.stream()
                .filter(r -> r.getReservationId().equals(reservationId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 정보입니다."));
        schedule.remove(toDelete);
    }

    public Reservations getReservations() {
        return reservations;
    }
}

package roomescape.domain;

import java.time.LocalDateTime;

public class Roomescape {

    private final Reservations schedule;
    private final PlayingTime playingTime;

    public Roomescape(Reservations schedule) {
        this.schedule = schedule;
        this.playingTime = PlayingTime.toDefaultPlayingTime();
    }

    public Reservation reserve(String customerName, LocalDateTime startTime) {
        ReservationTime reservationTime = playingTime.calculateReservationTime(startTime);
        if (schedule.hasOverlapTime(reservationTime)) {
            throw new IllegalArgumentException("해당 시간에 이미 예약된 정보가 있습니다.");
        }
        return new Reservation(customerName, reservationTime);
    }

    public void cancelReservation(Long reservationId) {
        schedule.removeById(reservationId);
    }

    public Reservations getSchedule() {
        return schedule;
    }
}

package roomescape.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Roomescape {

    private final ReservationSchedule reservations;
    private final PlayingTime playingTime;

    public Roomescape(ReservationSchedule reservations) {
        this.reservations = reservations;
        this.playingTime = PlayingTime.toDefaultPlayingTime();
    }

    public void reserve(String customerName, ReservationTime reservationTime) {
        LocalDateTime reservationStartTime = reservationTime.time();
        LocalDateTime reservationEndTime = reservationStartTime.plus(playingTime.getPlayingTime());

        List<Reservation> schedule = reservations.getSchedule();
        boolean hasOverlapTime = schedule.stream()
                .map(Reservation::getReservationTime)
                .anyMatch(existingTime -> {
                    LocalDateTime existingStartTime = existingTime.time();
                    LocalDateTime existingEndTime = existingStartTime.plus(playingTime.getPlayingTime());
                    return existingStartTime.isBefore(reservationEndTime) &&
                            reservationStartTime.isBefore(existingEndTime);
                });

        if (hasOverlapTime) {
            throw new IllegalArgumentException("해당 시간에 이미 예약된 정보가 있습니다.");
        }
        schedule.add(new Reservation(customerName, reservationTime));
    }

    public ReservationSchedule getReservations() {
        return reservations;
    }
}

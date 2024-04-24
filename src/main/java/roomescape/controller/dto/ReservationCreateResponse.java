package roomescape.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.entity.Reservation;

public class ReservationCreateResponse {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final GameTimeDto time;

    private class GameTimeDto {
        private final long id;
        private final LocalTime startAt;

        public GameTimeDto(long id, LocalTime startAt) {
            this.id = id;
            this.startAt = startAt;
        }

        public long getId() {
            return id;
        }

        public LocalTime getStartAt() {
            return startAt;
        }
    }

    public ReservationCreateResponse(long id, String name, LocalDate date, long timeId, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = new GameTimeDto(timeId, time);
    }

    public static ReservationCreateResponse from(Reservation reservation) {
        return new ReservationCreateResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getStartDate(),
                reservation.getReservationTimeId(),
                reservation.getStartTime());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public GameTimeDto getTime() {
        return time;
    }
}

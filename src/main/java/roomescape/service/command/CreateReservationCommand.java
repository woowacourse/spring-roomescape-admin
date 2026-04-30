package roomescape.service.command;

import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;

import java.time.LocalDate;

public class CreateReservationCommand {
    private final String name;
    private final LocalDate date;
    private final Long timeId;

    private CreateReservationCommand(String name, LocalDate date, Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public static CreateReservationCommand from(ReservationRequestDto reservationRequest) {
        return new CreateReservationCommand(
                reservationRequest.getName(),
                reservationRequest.getDate(),
                reservationRequest.getTimeId()
        );
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getTimeId() {
        return timeId;
    }
}

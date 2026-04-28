package roomescape.reservation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class RequestReservation {

    private final String name;
    private final LocalDate date;
    private final LocalTime time;
}

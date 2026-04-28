package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Reservation {

    private final long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

}

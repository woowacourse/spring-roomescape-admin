package roomescape;


import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class Reservation {
    @NonNull
    private final Long id;

    @NonNull
    private final String name;

    @NonNull
    private final LocalDate date;

    @NonNull
    private final ReservationTime time;
}

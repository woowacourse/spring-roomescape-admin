package roomescape;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Reservation {
    @NotNull
    private final Long id;

    @NotBlank
    private final String name;

    @NotNull
    private final LocalDate date;

    @NotNull
    private final ReservationTime time;
}

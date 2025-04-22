package roomescape;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
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
    @Future
    private final LocalDate date;
    @NotNull
    private final LocalTime time;
}

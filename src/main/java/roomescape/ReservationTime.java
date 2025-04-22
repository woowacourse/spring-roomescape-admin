package roomescape;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationTime {

    @NotNull
    private Long id;

    @NotNull
    private LocalTime startAt;
}

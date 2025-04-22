package roomescape.time;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import java.util.Objects;

public record TimeResponse(
        Long id,
        @JsonFormat(pattern = "HH:mm") LocalTime startAt
) {
        public TimeResponse{
                Objects.requireNonNull(id);
                Objects.requireNonNull(startAt);
        }
}

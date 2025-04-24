package roomescape.time.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import java.util.Objects;
import roomescape.time.Time;

public record TimeResponse(
        Long id,
        @JsonFormat(pattern = "HH:mm") LocalTime startAt
) {
        public TimeResponse{
                Objects.requireNonNull(id);
                Objects.requireNonNull(startAt);
        }

        public static TimeResponse createResponse(final Time time){
                return new TimeResponse(time.id(), time.startAt());
        }
}

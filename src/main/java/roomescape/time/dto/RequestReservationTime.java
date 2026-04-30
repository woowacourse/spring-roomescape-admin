package roomescape.time.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class RequestReservationTime {

    @JsonFormat(pattern = "HH:mm")
    private final LocalTime startAt;
}

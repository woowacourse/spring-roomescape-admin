package roomescape.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ReservationTimeCreateResponse(long id,
                                            @JsonFormat(pattern = "HH:mm")
                                            String startAt) {
}

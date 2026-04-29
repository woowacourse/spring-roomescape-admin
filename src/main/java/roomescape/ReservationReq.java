package roomescape;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record ReservationReq(
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        String name,
        Long timeId
) {
    @Override
    public String name() {
        return name;
    }

    @Override
    public LocalDate date() {
        return date;
    }

    @Override
    public Long timeId() {
        return timeId;
    }
}

package roomescape.reservationTime.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;

public class ReservationTimeResDto {

    @JsonProperty
    private final Long id;

    @JsonProperty
    private final LocalTime startAt;

    public ReservationTimeResDto(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }
}

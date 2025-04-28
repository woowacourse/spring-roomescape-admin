package roomescape.reservation_time.ui.dto;

import lombok.AccessLevel;
import lombok.experimental.FieldNameConstants;
import roomescape.common.validate.Validator;

import java.time.LocalTime;

@FieldNameConstants(level = AccessLevel.PRIVATE)
public record ReservationTimeRequestDto(LocalTime startAt) {

    public ReservationTimeRequestDto {
        validate(startAt);
    }

    private void validate(final LocalTime startAt) {
        Validator.of(ReservationTimeRequestDto.class)
                .notNullField(Fields.startAt, startAt);
    }
}

package roomescape.reservation_time.ui.dto;

import lombok.AccessLevel;
import lombok.experimental.FieldNameConstants;
import roomescape.common.validate.Validator;

import java.time.LocalTime;

@FieldNameConstants(level = AccessLevel.PRIVATE)
public record ReservationTimeResponseDto(Long id,
                                         LocalTime startAt) {

    public ReservationTimeResponseDto {
        validate(id, startAt);
    }

    private void validate(final Long id, final LocalTime startAt) {
        Validator.of(ReservationTimeResponseDto.class)
                .notNullField(ReservationTimeResponseDto.Fields.id, id)
                .notNullField(ReservationTimeResponseDto.Fields.startAt, startAt);
    }
}

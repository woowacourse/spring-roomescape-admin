package roomescape.reservation.ui.dto;

import lombok.AccessLevel;
import lombok.experimental.FieldNameConstants;
import roomescape.common.validate.Validator;
import roomescape.reservation_time.ui.dto.ReservationTimeResponseDto;

import java.time.LocalDate;

@FieldNameConstants(level = AccessLevel.PRIVATE)
public record ReservationResponseDto(Long id,
                                     String name,
                                     LocalDate date,
                                     ReservationTimeResponseDto time) {

    public ReservationResponseDto {
        validate(id, name, date, time);
    }

    private void validate(final Long id,
                          final String name,
                          final LocalDate date,
                          final ReservationTimeResponseDto time) {
        Validator.of(ReservationResponseDto.class)
                .notNullField(ReservationResponseDto.Fields.id, id)
                .notBlankField(ReservationResponseDto.Fields.name, name)
                .notNullField(ReservationResponseDto.Fields.date, date)
                .notNullField(ReservationResponseDto.Fields.time, time);
    }
}

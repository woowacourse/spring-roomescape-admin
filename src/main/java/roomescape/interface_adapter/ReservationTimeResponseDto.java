package roomescape.interface_adapter;

import java.time.LocalTime;
import roomescape.usecase.ReservationTimeOutput;

public record ReservationTimeResponseDto(Long id, LocalTime start_at) {
    public static ReservationTimeResponseDto from(final ReservationTimeOutput reservationTimeOutputModel) {
        return new ReservationTimeResponseDto(
                reservationTimeOutputModel.id(),
                reservationTimeOutputModel.startAt()
        );
    }
}

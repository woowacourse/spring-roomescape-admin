package roomescape.interface_adapter;

import java.time.LocalTime;
import roomescape.usecase.ReservationTimeOutputModel;

public record ReservationTimeResponseDto(Long id, LocalTime start_at) {
    public static ReservationTimeResponseDto from(final ReservationTimeOutputModel reservationTimeOutputModel) {
        return new ReservationTimeResponseDto(
                reservationTimeOutputModel.id(),
                reservationTimeOutputModel.startAt()
        );
    }
}

package roomescape.time.mapper;

import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.CreateResrvationTimeRequest;
import roomescape.time.dto.ReservationTimeResponse;
import roomescape.time.repository.entity.ReservationTimeEntity;

public class ReservationTimeMapper {

    private ReservationTimeMapper() {
    }

    public static ReservationTime toReservationTime(CreateResrvationTimeRequest from) {
        return new ReservationTime(from.getStartAt());
    }

    public static ReservationTime toReservationTime(ReservationTimeEntity from) {
        return new ReservationTime(from.getId(), from.getStartAt());
    }

    public static ReservationTimeResponse toReservationTimeResponse(ReservationTime from) {
        return new ReservationTimeResponse(from.getId(), from.getStartAt());
    }

    public static ReservationTimeEntity toReservationTimeEntity(ReservationTime from) {
        return new ReservationTimeEntity(from.getId(), from.getStartAt());
    }
}

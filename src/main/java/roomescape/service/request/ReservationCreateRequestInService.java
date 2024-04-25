package roomescape.service.request;

import roomescape.dto.request.ReservationCreateRequest;

public record ReservationCreateRequestInService(String name, String date, long timeId) {
    public static ReservationCreateRequestInService from(ReservationCreateRequest request) {
        return new ReservationCreateRequestInService(request.name(), request.date(), request.timeId());
    }
}

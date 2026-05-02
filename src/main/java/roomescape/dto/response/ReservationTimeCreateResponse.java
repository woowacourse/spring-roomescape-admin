package roomescape.dto.response;

public record ReservationTimeCreateResponse(
        Long id,
        String startAt
) {

    public static ReservationTimeCreateResponse of(Long id, String startAt) {
        return new ReservationTimeCreateResponse(id, startAt);
    }
}

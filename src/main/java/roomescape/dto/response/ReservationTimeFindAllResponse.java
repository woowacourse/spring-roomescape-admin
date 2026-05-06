package roomescape.dto.response;

public record ReservationTimeFindAllResponse(
        Long id,
        String startAt
) {

    public static ReservationTimeFindAllResponse of(Long id, String startAt) {
        return new ReservationTimeFindAllResponse(id, startAt);
    }
}

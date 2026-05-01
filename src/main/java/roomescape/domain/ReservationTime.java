package roomescape.domain;

public record ReservationTime(
        Long id,
        String startAt
) {
    public ReservationTime {
        validateStartAtNotBlank(startAt);
    }

    private static void validateStartAtNotBlank(String startAt) {
        if (startAt == null || startAt.isBlank()) {
            throw new IllegalArgumentException("startAt은 도메인에서 필수값이며 공백일 수 없습니다.");
        }
    }

    public static ReservationTime constructWithoutId(String startAt) {
        return new ReservationTime(null, startAt);
    }
}

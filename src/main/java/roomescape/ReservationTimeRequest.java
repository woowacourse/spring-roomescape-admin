package roomescape;

public record ReservationTimeRequest(String startAt) {

    public ReservationTime toEntity(Long id) {
        return new ReservationTime(id, this.startAt);
    }
}

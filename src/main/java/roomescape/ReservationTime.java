package roomescape;

public record ReservationTime(
        Long id,
        String startAt) {

    public ReservationTime toEntity(Long id) {
        return new ReservationTime(id, this.startAt);
    }

}

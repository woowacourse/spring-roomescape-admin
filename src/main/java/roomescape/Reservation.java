package roomescape;

public record Reservation(
        Long id,
        String name,
        String date,
        String time) {

    public Reservation toEntity(Long id) {
        return new Reservation(id, this.name, this.date, this.time);
    }

}

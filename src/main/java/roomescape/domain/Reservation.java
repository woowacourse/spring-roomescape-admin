package roomescape.domain;

public record Reservation(long id, String name, String date, String time) {

    public boolean checkSameId(long id) {
        return this.id == id;
    }
}

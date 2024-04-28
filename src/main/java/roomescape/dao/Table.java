package roomescape.dao;

public enum Table {
    RESERVATION("reservation"),
    RESERVATION_TIME("reservation_time"),
    ;
    private final String name;

    Table(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

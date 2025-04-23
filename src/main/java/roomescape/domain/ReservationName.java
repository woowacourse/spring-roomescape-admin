package roomescape.domain;

public class ReservationName {

    private final String name;

    public ReservationName(final String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(final String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("예약자 이름은 null이 될 수 없습니다.");
        }
    }

    public String getName() {
        return name;
    }
}

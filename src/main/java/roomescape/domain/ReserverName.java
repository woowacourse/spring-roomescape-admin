package roomescape.domain;

public class ReserverName {

    private final String name;

    public ReserverName(final String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(final String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("예약자 이름은 빈 칸이 될 수 없습니다.");
        }
    }

    public String getName() {
        return name;
    }
}

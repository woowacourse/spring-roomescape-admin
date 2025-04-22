package roomescape.business.domain;

public class Customer {

    private final String name;

    public Customer(final String name) {
        validateName(name);
        this.name = name;
    }

    private static void validateName(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("예약자명은 null이 될 수 없습니다.");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("예약자명은 비어 있을 수 없습니다.");
        }
        if (name.length() > 10) {
            throw new IllegalArgumentException("예약자명은 최대 10자 입니다.");
        }
    }

    public String name() {
        return name;
    }
}

package roomescape.entity;

public class Name {
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 5;

    private final String name;

    public Name(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        validateNonNull(name);
        validateLength(name);
    }

    private void validateNonNull(String name) {
        if (name == null) {
            throw new NullPointerException("예약자 이름은 Null이 될 수 없습니다");
        }
    }

    private void validateLength(String name) {
        if (MAX_LENGTH < name.length() || name.length() < MIN_LENGTH) {
            throw new IllegalArgumentException(
                    "예약자 이름은 " + MIN_LENGTH + "자 이상, " + MAX_LENGTH + "자 미만이어야 합니다: " + name);
        }
    }

    public String getName() {
        return name;
    }
}

package roomescape.business.domain;

import org.springframework.util.ObjectUtils;

public class Customer {

    private static final int MAX_NAME_LENGTH = 10;

    private final String name;

    public Customer(final String name) {
        if (ObjectUtils.isEmpty(name)) {
            throw new IllegalArgumentException("예약자명은 null이 될 수 없습니다.");
        }
        validateNameLength(name);
        this.name = name;
    }

    private static void validateNameLength(final String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("예약자명은 최대 10자 입니다.");
        }
    }

    public String name() {
        return name;
    }
}

package roomescape.domain;

import java.util.Arrays;

public enum Role {
    ADMIN,
    USER;

    public static Role match(String role) {
        return Arrays.stream(Role.values())
                .filter(r -> r.toString().equals(role.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("매칭되는 Role이 없습니다. role = " + role));
    }
}

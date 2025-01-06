package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoleTest {

    @Test
    @DisplayName("String으로 역할을 찾는다.")
    void match() {
        // when
        Role admin = Role.match("admin");
        Role user = Role.match("user");

        // then
        assertThat(admin).isEqualTo(Role.ADMIN);
        assertThat(user).isEqualTo(Role.USER);
    }
}

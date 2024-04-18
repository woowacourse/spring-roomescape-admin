package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NameTest {

    @Test
    @DisplayName("동등성을 비교한다.")
    void equalsTest() {
        Name name = new Name("웨지");
        assertThat(name).isEqualTo(new Name("웨지"));
    }
}
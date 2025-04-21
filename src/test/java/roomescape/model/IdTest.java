package roomescape.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IdTest {

    @DisplayName("아이디 값은 1씩 증가하는 기능을 구현한다")
    @Test
    void checkIdIncrement() {
        Id id1 = new Id();
        Id id2 = new Id();

        assertThat(id2.getIdValue() - id1.getIdValue()).isEqualTo(1L);
    }
}

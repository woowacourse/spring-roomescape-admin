package roomescape.domain_entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IdTest {
    @Test
    @DisplayName("id값은 1부터 1씩 증가하며 생성된다.")
    void idIncrementTest() {
        Id id1 = new Id();
        Id id2 = new Id();

        assertThat(id2.getValue() - id1.getValue()).isEqualTo(1L);
    }
}

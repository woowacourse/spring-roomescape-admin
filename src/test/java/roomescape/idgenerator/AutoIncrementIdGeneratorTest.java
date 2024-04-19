package roomescape.idgenerator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AutoIncrementIdGeneratorTest {
    @DisplayName("id 생성 테스트")
    @Test
    void generateNewId() {
        IdGenerator idGenerator = new AutoIncrementIdGenerator();

        assertAll(
                () -> assertThat(idGenerator.generateNewId()).isEqualTo(1),
                () -> assertThat(idGenerator.generateNewId()).isEqualTo(2),
                () -> assertThat(idGenerator.generateNewId()).isEqualTo(3)
        );
    }
}

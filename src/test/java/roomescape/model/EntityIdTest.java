package roomescape.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EntityIdTest {

    @Test
    void id는_null이_될_수_없다() {
        // Given
        // When
        // Then
        assertThatThrownBy(() -> new EntityId(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 id가 아닙니다.");
    }

    @Test
    void _1_미만의_id로_생성할_경우_예외가_발생한다() {
        // Given
        // When
        // Then
        assertThatThrownBy(() -> new EntityId(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("id는 1 이상 가능합니다.");
    }

    @Test
    void id를_부여하지_않을_경우_마이너스1의_id를_가진_EntityId_객체가_생성된다() {
        // Given
        // When
        EntityId entityId = EntityId.generateUnassigned();

        // Then
        assertThat(entityId.getId()).isEqualTo(-1L);
    }
}

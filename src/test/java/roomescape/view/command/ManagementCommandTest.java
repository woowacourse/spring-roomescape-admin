package roomescape.view.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ManagementCommandTest {


    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "2"})
    void commandCreationTest(String input) {
        assertDoesNotThrow(() -> ManagementCommand.from(input));
    }

    @Test
    void invalidCreationTest() {
        assertThatThrownBy(() -> ManagementCommand.from("3"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 명령어입니다.");
    }

    @Test
    void backCommandTest() {
        ManagementCommand backCommand = ManagementCommand.BACK;
        assertThat(backCommand.isBack()).isTrue();
    }
}

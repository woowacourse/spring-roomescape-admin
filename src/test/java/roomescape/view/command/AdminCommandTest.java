package roomescape.view.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AdminCommandTest {

    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "2"})
    @DisplayName("입력으로 커맨드를 생성한다.")
    void commandCreationTest(String input) {
        assertDoesNotThrow(() -> AdminCommand.from(input));
    }

    @Test
    @DisplayName("존재하지 않는 입력으로 커맨드를 생성하는 경우, 예외를 발생한다.")
    void invalidCreationTest() {
        assertThatThrownBy(() -> AdminCommand.from("3"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 명령어입니다.");
    }

    @Test
    @DisplayName("커맨드가 종료 커맨드인지 확인한다.")
    void exitCommandTest() {
        AdminCommand exitCommand = AdminCommand.EXIT;
        assertThat(exitCommand.isExit()).isTrue();
    }
}

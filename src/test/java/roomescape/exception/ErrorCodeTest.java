package roomescape.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ErrorCodeTest {

    @Test
    @DisplayName("id값을 포함하는 메시지의 경우 동적으로 id값을 넣어줄 수 있다.")
    void getFormattedMessage() {
        // given
        ErrorCode errorCode = ErrorCode.NOT_FOUND_MEMBER;

        // when
        String result = errorCode.getFormattedMessage(1);

        // then
        assertThat(result).contains("memberId = 1");
    }
}

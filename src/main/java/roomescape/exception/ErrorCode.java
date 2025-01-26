package roomescape.exception;

import static roomescape.domain.Member.MAX_NAME_LENGTH;
import static roomescape.domain.Member.MIN_NAME_LENGTH;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 예약
    DUPLICATED_WAITING("이미 예약에 성공했거나, 대기에 성공했기 때문에 추가로 대기할 수 없습니다."),
    DUPLICATED_RESERVATION("이미 예약이 존재합니다."),
    WAITING_WITHOUT_RESERVATION("앞선 예약이 존재하지 않아 대기가 불가능합니다."),
    PAST_TIME_RESERVATION_NOT_ALLOWED("현재 시각보다 이전의 예약은 불가능합니다."),
    // 관리자 기능
    CANNOT_DELETE_TIME_WITH_RESERVATION("예약이 존재하는 시간은 삭제할 수 없습니다."),
    CANNOT_DELETE_THEME_WITH_RESERVATION("예약이 존재하는 테마는 삭제할 수 없습니다."),
    // 권한
    FORBIDDEN_RESERVATION_CANCEL("예약을 취소할 권한이 없습니다."),
    FORBIDDEN_ADMIN_ACCESS("관리자만 접근할 수 있는 요청입니다."),
    // NOT_FOUND
    NOT_FOUND_MEMBER("회원이 존재하지 않습니다. memberId = %d"),
    NOT_FOUND_TIME("예약 시간이 존재하지 않습니다. timeId = %d"),
    NOT_FOUND_THEME("테마가 존재하지 않습니다. themeId = %d"),
    // 로그인
    WRONG_EMAIL("이메일을 다시 확인해주세요."),
    WRONG_PASSWORD("비밀번호가 일치하지 않습니다."),
    INVALID_TOKEN("토큰이 유효하지 않습니다."),
    EXPIRED_TOKEN("만료된 토큰입니다."),
    NOT_EXIST_TOKEN("토큰이 존재하지 않습니다."),
    // 회원가입
    INVALID_MEMBER_NAME_LENGTH(String.format("예약자의 이름은 %d자 이상 %d자 이하여야 합니다.", MIN_NAME_LENGTH, MAX_NAME_LENGTH));

    private final String message;

    public String getFormattedMessage(Object... args) {
        return String.format(this.message, args);
    }
}

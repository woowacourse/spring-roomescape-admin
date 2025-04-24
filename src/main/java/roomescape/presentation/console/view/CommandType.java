package roomescape.presentation.console.view;

import java.util.Arrays;

public enum CommandType {
    예약_전체_조회(1),
    예약_추가(2),
    예약_삭제(3),
    예약_시간_전체_조회(4),
    예약_시간_추가(5),
    예약_시간_삭제(6),
    종료(7),
    ;

    private final int optionalNumber;

    CommandType(final int optionalNumber) {
        this.optionalNumber = optionalNumber;
    }

    public static CommandType from(int optionalNumber) {
        return Arrays.stream(values())
                .filter(type -> type.optionalNumber == optionalNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 번호에 해당하는 명령이 없습니다."));
    }
}

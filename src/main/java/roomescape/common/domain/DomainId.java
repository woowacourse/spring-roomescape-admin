package roomescape.common.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class DomainId {

    private final Long value;
    private final boolean assigned;

    public Long getValue() {
        if (assigned) {
            return value;
        }
        throw new IllegalStateException("저장되지 않아서 식별할 수 없습니다.");
    }
}

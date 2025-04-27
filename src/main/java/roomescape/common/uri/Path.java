package roomescape.common.uri;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Path {

    private final String value;

    public static Path from(final String value) {
        validate(value);
        return new Path(value);
    }

    public static Path fromLeadingSlash(final String value) {
        if (!value.startsWith("/")) {
            throw new IllegalArgumentException("경로는 /로 시작해야 합니다.");
        }
        return Path.from(value.substring(1));
    }

    private static void validate(final String value) {
        validateNotBlankOrNull(value);
        validateNoSlashIncluded(value);
    }

    private static void validateNoSlashIncluded(final String value) {
        if (value.contains("/")) {
            throw new IllegalArgumentException("경로는 /를 포함할 수 없습니다");
        }
    }

    private static void validateNotBlankOrNull(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("경로는 null이거나 비어있을 수 없습니다");
        }
    }

    public String withLeadingSlash() {
        return "/" + value;
    }
}

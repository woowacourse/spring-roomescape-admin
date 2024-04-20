package roomescape.domain.reservation;

import java.util.regex.Pattern;

public class Name {
    private static final int MIN_NAME_LENGTH = 1;
    private static final String NAME_RULE = "[가-힣a-zA-Z]{" + MIN_NAME_LENGTH + ",}";
    private static final Pattern NAME_RULE_MATCHER = Pattern.compile(NAME_RULE);

    private final String name;

    public Name(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (name != null && NAME_RULE_MATCHER.matcher(name).matches()) {
            return;
        }
        throw new IllegalArgumentException("이름은 " + MIN_NAME_LENGTH + "글자 이상의 영/한글이어야 합니다");
    }

    public String getName() {
        return name;
    }
}

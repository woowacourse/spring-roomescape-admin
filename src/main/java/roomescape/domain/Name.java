package roomescape.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Name {
    private static final Pattern PATTERN = Pattern.compile("[가-힣a-zA-Z-_0-9]+");
    private final String name;

    public Name(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        validateBlank(name);
        validateLength(name);
        validateCharacter(name);
    }

    private void validateBlank(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름에 공백을 입력할 수 없습니다.");
        }
    }

    private void validateLength(String name) {
        if (name.length() > 5) {
            throw new IllegalArgumentException("이름의 길이는 5 이하여야 합니다.");
        }
    }

    private void validateCharacter(String name) {
        Matcher matcher = PATTERN.matcher(name);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("이름은 알파벳, 한글, 숫자, '_', '-'로만 이루어져야 합니다.");
        }
    }

    public String getName() {
        return name;
    }
}

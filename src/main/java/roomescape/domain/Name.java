package roomescape.domain;

record Name(String value) {

    Name {
        if (value.isBlank()) {
            throw new IllegalArgumentException("이름은 공백을 제외한 1자 이상이어야 합니다.");
        }
    }
}

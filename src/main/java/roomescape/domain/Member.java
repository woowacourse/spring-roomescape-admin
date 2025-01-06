package roomescape.domain;

import io.micrometer.common.util.StringUtils;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import roomescape.exception.BadRequestException;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Member {

    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_NAME_LENGTH = 20;

    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;

    public Member(String name, String email, String password, Role role) {
        // TODO: email, password 검증 로직
        validateName(name);
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Member(Long id, Member member) {
        this(id, member.getName(), member.getEmail(), member.getPassword(), member.getRole());
    }

    public void validateName(String name) {
        if (StringUtils.isBlank(name) || name.length() > MAX_NAME_LENGTH) {
            throw new BadRequestException(
                    String.format("예약자의 이름은 %d자 이상 %d자 이하여야 합니다.", MIN_NAME_LENGTH, MAX_NAME_LENGTH));
        }
    }

    public boolean isNotCorrectPassword(String password) {
        return !Objects.equals(this.password, password);
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }
}

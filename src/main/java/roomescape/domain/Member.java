package roomescape.domain;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import roomescape.exception.BadRequestException;
import roomescape.exception.ErrorCode;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class Member extends BaseEntity {

    public static final int MIN_NAME_LENGTH = 1;
    public static final int MAX_NAME_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
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
            throw new BadRequestException(ErrorCode.INVALID_MEMBER_NAME_LENGTH);
        }
    }

    public boolean isNotCorrectPassword(String password) {
        return !Objects.equals(this.password, password);
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }
}

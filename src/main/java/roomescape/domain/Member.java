package roomescape.domain;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Member {

    private Long id;
    private String name;
    private String email;
    private String password;

    public Member(String name, String email, String password) {
        // TODO: email, password 검증 로직
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Member(Long id, Member member) {
        this(id, member.getName(), member.getEmail(), member.getPassword());
    }

    public boolean isNotCorrectPassword(String password) {
        return !Objects.equals(this.password, password);
    }
}

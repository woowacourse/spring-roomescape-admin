package roomescape.global.query.condition;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import roomescape.global.query.join.JoinCondition;

class JoinConditionTest {
    @Test
    void on절을_생성한다() {
        StringBuilder builder = new StringBuilder();
        JoinCondition joinCondition = JoinCondition.on("u.id", "o.user_id");
        joinCondition.assemble(builder);

        assertThat(builder.toString()).isEqualTo(" ON u.id = o.user_id");
    }
}

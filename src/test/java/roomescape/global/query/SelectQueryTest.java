package roomescape.global.query;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import roomescape.global.query.condition.ComparisonCondition;

class SelectQueryTest {
    @Test
    void 컬럼을_추가한다() {
        SelectQuery selectQuery = new SelectQuery("users")
                .addColumns("id", "name");

        assertThat(selectQuery.build()).isEqualTo("SELECT id, name FROM users");
    }

    @Test
    void 모든_컬럼을_추가한다() {
        SelectQuery selectQuery = new SelectQuery("users")
                .addAllColumns();

        assertThat(selectQuery.build()).isEqualTo("SELECT * FROM users");
    }

    @Test
    void WHERE_절을_추가한다() {
        SelectQuery selectQuery = new SelectQuery("users")
                .addColumns("id", "name")
                .where(ComparisonCondition.equalTo("id", 1));

        assertThat(selectQuery.build()).isEqualTo("SELECT id, name FROM users WHERE id = 1");
    }
}

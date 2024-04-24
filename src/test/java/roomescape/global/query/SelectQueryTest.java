package roomescape.global.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    void alias를_사용한다() {
        SelectQuery selectQuery = new SelectQuery("users")
                .alias("u")
                .addColumns("id", "name");

        assertThat(selectQuery.build()).isEqualTo("SELECT u.id, u.name FROM users AS u");
    }

    @Test
    void 컬럼을_추가하고_alias를_사용하면_예외가_발생한다() {
        SelectQuery selectQuery = new SelectQuery("users")
                .addColumns("id", "name");

        assertThatThrownBy(() -> selectQuery.alias("u"))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("alias를 먼저 지정하고 columns을 지정해야 합니다.");
    }

    @Test
    void 모든_컬럼을_조회하는_JOIN_절을_추가한다() {
        SelectQuery selectQuery = new SelectQuery("users")
                .alias("u")
                .addAllColumns()
                .join("INNER", "orders", ComparisonCondition.equalTo("u.id", "o.user_id"), "o");

        assertThat(selectQuery.build()).isEqualTo(
                "SELECT * FROM users AS u INNER JOIN orders AS o ON u.id = o.user_id");
    }

    @Test
    void base_테이블의_컬럼만_조회하는_JOIN_절을_추가한다() {
        SelectQuery selectQuery = new SelectQuery("users")
                .alias("u")
                .addColumns("id", "name")
                .join("INNER", "orders", ComparisonCondition.equalTo("u.id", "o.user_id"), "o");

        assertThat(selectQuery.build()).isEqualTo(
                "SELECT u.id, u.name FROM users AS u INNER JOIN orders AS o ON u.id = o.user_id");
    }

    @Test
    void join_테이블의_컬럼도_조회하는_JOIN_절을_추가한다() {
        SelectQuery selectQuery = new SelectQuery("users")
                .alias("u")
                .addColumns("id", "name")
                .join("INNER", "orders", ComparisonCondition.equalTo("u.id", "o.user_id"), "o")
                .addJoinColumns("price");

        assertThat(selectQuery.build()).isEqualTo(
                "SELECT u.id, u.name, o.price FROM users AS u INNER JOIN orders AS o ON u.id = o.user_id");
    }
}

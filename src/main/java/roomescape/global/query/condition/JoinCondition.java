package roomescape.global.query.condition;

public class JoinCondition {
    private final ComparisonCondition condition;

    private JoinCondition(ComparisonCondition condition) {
        this.condition = condition;
    }

    public static JoinCondition on(String column, Object value) {
        return new JoinCondition(ComparisonCondition.equalTo(column, value));
    }

    public String build() {
        return condition.build().replace("'", "");
    }
}

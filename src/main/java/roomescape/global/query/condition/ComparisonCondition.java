package roomescape.global.query.condition;

public class ComparisonCondition {
    private final String column;
    private final Object value;
    private final String operator;

    private ComparisonCondition(String column, Object value, String operator) {
        this.column = column;
        this.value = value;
        this.operator = operator;
    }

    public static ComparisonCondition equalTo(String column, Object value) {
        return new ComparisonCondition(column, value, " = ");
    }

    public String build() {
        return column + operator + value;
    }
}

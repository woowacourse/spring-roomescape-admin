package roomescape.global.query.condition;

import roomescape.global.query.Assemblable;

public class ComparisonCondition implements Assemblable {
    private final String column;
    private final Object value;
    private final String operator;
    private boolean useSingleQuotes;

    private ComparisonCondition(String column, Object value, String operator) {
        this.column = column;
        this.value = value;
        this.operator = operator;
        this.useSingleQuotes = true;
    }

    public static ComparisonCondition equalTo(String column, Object value) {
        return new ComparisonCondition(column, value, " = ");
    }

    public void useSingleQuotes(boolean useSingleQuotes) {
        this.useSingleQuotes = useSingleQuotes;
    }

    @Override
    public void assemble(StringBuilder builder) {
        builder.append(column)
                .append(operator)
                .append(value());
    }

    private String value() {
        if (useSingleQuotes) {
            return "'" + value + "'";
        }
        return value.toString();
    }
}

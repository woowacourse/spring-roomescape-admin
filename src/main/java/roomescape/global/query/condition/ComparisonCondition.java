package roomescape.global.query.condition;

import roomescape.global.query.Assemblable;

public class ComparisonCondition implements Assemblable {
    private final String column;
    private final Object value;
    private final Operator operator;
    private boolean useSingleQuotes;

    private ComparisonCondition(String column, Object value, Operator operator) {
        this.column = column;
        this.value = value;
        this.operator = operator;
        this.useSingleQuotes = true;
    }

    public static ComparisonCondition equalTo(String column, Object value) {
        return new ComparisonCondition(column, value, Operator.EQUAL_TO);
    }

    public void useSingleQuotes(boolean useSingleQuotes) {
        this.useSingleQuotes = useSingleQuotes;
    }

    @Override
    public void assemble(StringBuilder builder) {
        builder.append(column)
                .append(operator.value())
                .append(value());
    }

    private String value() {
        if (useSingleQuotes) {
            return "'" + value + "'";
        }
        return value.toString();
    }

    enum Operator {
        EQUAL_TO(" = "),
        NOT_EQUAL_TO(" != "),
        GREATER_THAN(" > "),
        LESS_THAN(" < ");

        private final String value;

        Operator(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }
}

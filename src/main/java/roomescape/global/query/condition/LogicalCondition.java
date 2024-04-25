package roomescape.global.query.condition;

import java.util.ArrayList;
import java.util.List;

public class LogicalCondition {
    private final String operator;
    private final List<ComparisonCondition> conditions;

    private LogicalCondition(String operator) {
        this.operator = operator;
        this.conditions = new ArrayList<>();
    }

    public static LogicalCondition and() {
        return new LogicalCondition(" AND ");
    }

    public void addCondition(ComparisonCondition condition) {
        conditions.add(condition);
    }

    public boolean isEmpty() {
        return conditions.isEmpty();
    }

    public String build() {
        return String.join(operator, buildConditions());
    }

    private List<String> buildConditions() {
        return conditions.stream()
                .map(ComparisonCondition::build)
                .toList();
    }
}

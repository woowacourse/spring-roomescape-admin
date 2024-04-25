package roomescape.global.query.condition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import roomescape.global.query.Assemblable;

public class LogicalCondition implements Assemblable {
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

    @Override
    public void assemble(StringBuilder builder) {
        Iterator<ComparisonCondition> conditionIterator = conditions.iterator();
        while (conditionIterator.hasNext()) {
            conditionIterator.next().assemble(builder);
            appendOperator(builder, conditionIterator.hasNext());
        }
    }

    private void appendOperator(StringBuilder builder, boolean hasNext) {
        if (hasNext) {
            builder.append(operator);
        }
    }
}

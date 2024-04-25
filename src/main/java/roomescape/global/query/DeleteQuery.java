package roomescape.global.query;

import java.util.Objects;
import roomescape.global.query.condition.ComparisonCondition;
import roomescape.global.query.condition.LogicalCondition;

public class DeleteQuery extends Query {
    private final String table;
    private final LogicalCondition condition;

    protected DeleteQuery(String table) {
        Objects.requireNonNull(table, "테이블명은 필수입니다.");
        this.table = table;
        this.condition = LogicalCondition.and();
    }

    public DeleteQuery where(ComparisonCondition condition) {
        this.condition.addCondition(condition);
        return this;
    }

    @Override
    public void assemble(StringBuilder builder) {
        builder.append("DELETE FROM ")
                .append(table);
        if (!condition.isEmpty()) {
            builder.append(" WHERE ");
            condition.assemble(builder);
        }
    }
}

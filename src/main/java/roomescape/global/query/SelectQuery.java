package roomescape.global.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import roomescape.global.query.condition.ComparisonCondition;
import roomescape.global.query.condition.LogicalCondition;

public class SelectQuery {
    private final String table;
    private final List<String> columns;
    private final LogicalCondition condition;

    protected SelectQuery(String table) {
        Objects.requireNonNull(table, "테이블명은 필수입니다.");
        this.table = table;
        this.columns = new ArrayList<>();
        this.condition = LogicalCondition.and();
    }

    public SelectQuery addColumns(String... columnNames) {
        this.columns.addAll(Arrays.asList(columnNames));
        return this;
    }

    public SelectQuery addAllColumns() {
        this.columns.add("*");
        return this;
    }

    public SelectQuery where(ComparisonCondition condition) {
        this.condition.addCondition(condition);
        return this;
    }

    public String build() {
        return "SELECT " + buildColumnsAndTable() + buildCondition();
    }

    private String buildColumnsAndTable() {
        if (columns.isEmpty()) {
            throw new IllegalArgumentException("지정된 컬럼이 없습니다.");
        }
        return String.join(", ", columns) + " FROM " + table;
    }

    private String buildCondition() {
        if (!condition.isEmpty()) {
            return " WHERE " + condition.build();
        }
        return "";
    }
}

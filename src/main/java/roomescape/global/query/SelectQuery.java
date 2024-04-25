package roomescape.global.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import roomescape.global.query.condition.ComparisonCondition;
import roomescape.global.query.condition.JoinCondition;
import roomescape.global.query.condition.LogicalCondition;

public class SelectQuery {
    private final String table;
    private final List<String> columns;
    private final LogicalCondition condition;
    private Join join = Join.EMPTY;
    private String alias;

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

    public SelectQuery alias(String alias) {
        if (alias == null || alias.isBlank()) {
            throw new IllegalArgumentException("alias가 비어있습니다.");
        }
        this.alias = alias;
        return this;
    }

    public SelectQuery join(String joinType, String joinTable, JoinCondition joinCondition, String alias) {
        join = new Join(joinType, joinTable, joinCondition, alias);
        return this;
    }

    public String build() {
        return "SELECT " + buildColumnsAndTable() + buildJoin() + buildCondition();
    }

    private String buildColumnsAndTable() {
        if (columns.isEmpty()) {
            throw new IllegalArgumentException("지정된 컬럼이 없습니다.");
        }
        if (alias != null) {
            return String.join(", ", columns) + " FROM " + table + " AS " + alias;
        }
        return String.join(", ", columns) + " FROM " + table;
    }

    private String buildCondition() {
        if (!condition.isEmpty()) {
            return " WHERE " + condition.build();
        }
        return "";
    }

    private String buildJoin() {
        if (!join.isEmpty()) {
            return join.build();
        }
        return "";
    }

    private static class Join {
        private static final Join EMPTY = new Join("", "", null); // todo overriding

        private final String joinType;
        private final String table;
        private final JoinCondition condition;
        private String alias;

        public Join(String joinType, String table, JoinCondition condition) {
            this(joinType, table, condition, null);
        }

        public Join(String joinType, String table, JoinCondition condition, String alias) {
            this.joinType = joinType;
            this.table = table;
            this.condition = condition;
            this.alias = alias;
        }

        public boolean isEmpty() { // todo 삭제
            return this == EMPTY;
        }

        public String build() {
            if (alias != null) {
                return " " + joinType + " JOIN " + table + " AS " + alias + " ON " + condition.build();
            }
            return " " + joinType + " JOIN " + table + " ON " + condition.build();
        }
    }
}

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
    private Join join = Join.EMPTY;
    private String alias;

    protected SelectQuery(String table) {
        Objects.requireNonNull(table, "테이블명은 필수입니다.");
        this.table = table;
        this.columns = new ArrayList<>();
        this.condition = LogicalCondition.and();
    }

    public SelectQuery addColumns(String... columnNames) {
        if (alias != null) {
            columnNames = Arrays.stream(columnNames)
                    .map(s -> alias + "." + s)
                    .toArray(String[]::new);
        }
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
        if (!columns.isEmpty()) {
            throw new IllegalArgumentException("alias를 먼저 지정하고 columns을 지정해야 합니다.");
        }
        this.alias = alias;
        return this;
    }

    public SelectQuery join(String joinType, String joinTable, ComparisonCondition comparisonCondition, String alias) {
        join = new Join(joinType, joinTable, comparisonCondition, alias);
        return this;
    }

    public SelectQuery addJoinColumns(String... columnNames) {
        if (join.isEmpty()) {
            throw new IllegalArgumentException("조인이 없습니다.");
        }
        if (join.hasAlias()) {
            this.columns.addAll(Arrays.stream(columnNames)
                    .map(s -> join.alias + "." + s)
                    .toList());
        }
        return this;
    }

    public String build() {
        return "SELECT " + buildColumnsAndTable() + buildCondition() + buildJoin();
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
        private static final Join EMPTY = new Join("", "", null, null);

        private final String joinType;
        private final String table;
        private final ComparisonCondition condition;
        private final String alias;

        public Join(String joinType, String table, ComparisonCondition condition, String alias) {
            this.joinType = joinType;
            this.table = table;
            this.condition = condition;
            this.alias = alias;
        }

        public boolean isEmpty() {
            return this == EMPTY;
        }

        public boolean hasAlias() {
            return alias != null;
        }

        public String build() {
            if (alias != null) {
                return " " + joinType + " JOIN " + table + " AS " + alias + " ON " + condition.build();
            }
            return " " + joinType + " JOIN " + table + " ON " + condition.build();
        }
    }
}

package roomescape.model;

public interface Entity<T extends Entity<T>> {

    String DEFAULT_ID_COLUMN_NAME = "id";

    T withId(Long id);

    default String idColumnName() {
        return DEFAULT_ID_COLUMN_NAME;
    }
}

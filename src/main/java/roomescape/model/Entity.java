package roomescape.model;

public interface Entity {

    String DEFAULT_ID_COLUMN_NAME = "id";

    <T extends Entity> T withId(Long id);

    default String idColumnName() {
        return DEFAULT_ID_COLUMN_NAME;
    }
}

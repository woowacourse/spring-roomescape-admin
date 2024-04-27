package roomescape.console.view.dto;

public enum FunctionCommand {
    FIND_ALL,
    CREATE,
    DELETE,
    ;

    public boolean isFindAll() {
        return this == FIND_ALL;
    }

    public boolean isCreate() {
        return this == CREATE;
    }

    public boolean isDelete() {
        return this == DELETE;
    }

}

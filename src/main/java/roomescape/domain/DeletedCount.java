package roomescape.domain;

public class DeletedCount {

    private static final int NOT_DELETED_COUNT = 0;
    
    private final int deletedCount;

    public DeletedCount(int deletedCount) {
        this.deletedCount = deletedCount;
    }

    public boolean isNotDelete() {
        return deletedCount <= NOT_DELETED_COUNT;
    }
}

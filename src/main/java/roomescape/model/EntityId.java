package roomescape.model;

public class EntityId {

    public static final long UNASSIGNED_ID_VALUE = -1L;
    public static final int MINIMUM_VALUE = 1;
    private final Long id;

    private EntityId() {
        this.id = UNASSIGNED_ID_VALUE;
    }

    public EntityId(Long id) {
        validate(id);
        this.id = id;
    }

    private static void validate(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("올바른 id가 아닙니다.");
        }
        if (id < MINIMUM_VALUE) {
            throw new IllegalArgumentException("id는 1 이상 가능합니다.");
        }
    }

    public static EntityId generateUnassigned() {
        return new EntityId();
    }

    public Long getId() {
        return id;
    }
}

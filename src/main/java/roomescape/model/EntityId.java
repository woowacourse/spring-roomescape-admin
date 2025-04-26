package roomescape.model;

public class EntityId {

    public static final long UNASSIGNED_ID_VALUE = -1L;
    public static final int MINIMUM_VALUE = 1;
    private final Long id;

    private EntityId(Long id) {
        this.id = id;
    }

    public static EntityId generate(Long id) {
        validate(id);
        return new EntityId(id);
    }

    private static void validate(Long id) {
        if (id < MINIMUM_VALUE) {
            throw new IllegalArgumentException("올바른 id가 아닙니다.");
        }
    }

    public static EntityId generateUnassigned() {
        return new EntityId(UNASSIGNED_ID_VALUE);
    }

    public Long getId() {
        return id;
    }
}

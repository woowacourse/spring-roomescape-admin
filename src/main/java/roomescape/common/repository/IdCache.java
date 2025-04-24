package roomescape.common.repository;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Component
public class IdCache<T> {
    private final Map<T, Long> cache = new HashMap<>();

    public void cacheId(T item, Long id) {
        cache.put(item, id);
    }

    public Long getCachedId(T item) {
        return cache.get(item);
    }
}

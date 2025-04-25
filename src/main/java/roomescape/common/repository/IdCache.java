package roomescape.common.repository;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Component
public abstract class IdCache<T> {
    private final Map<T, Long> cache = new HashMap<>();

    public void cacheId(T domain, Long id) {
        cache.put(domain, id);
    }

    public Long getCachedId(T domain) {
        return cache.get(domain);
    }
}

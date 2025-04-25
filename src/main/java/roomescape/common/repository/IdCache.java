package roomescape.common.repository;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import roomescape.common.domain.Cacheable;

@RequestScope
@Component
public class IdCache {
    private final Map<Cacheable, Long> cache = new HashMap<>();

    public void cacheId(Cacheable domain, Long id) {
        cache.put(domain, id);
    }

    public Long getCachedId(Cacheable domain) {
        return cache.get(domain);
    }
}

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

    public IdCache() {
        System.out.println("Bean created, cache = " + cache);
    }

    public void cacheId(Cacheable domain, Long id) {
        cache.put(domain, id);
        System.out.println("cache = " + cache);
        System.out.println("cached domain = " + domain);
    }

    public Long getCachedId(Cacheable domain) {
        System.out.println("cache = " + cache);
        System.out.println("get cached domain = " + domain);
        return cache.get(domain);
    }
}

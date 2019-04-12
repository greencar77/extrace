package com.rabarbers.call.publish;

import com.rabarbers.call.domain.Domain;
import com.rabarbers.call.pattern.Pattern;
import com.rabarbers.call.pattern.image.PatternImageProducer;

import java.util.Map;

public interface DomainPublisher {
    void publishTraceDetails(Domain domain, String path, Map<Class<? extends Pattern>, PatternImageProducer<?>> patternImageProducerMap);
}

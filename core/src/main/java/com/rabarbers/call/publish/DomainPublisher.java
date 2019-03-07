package com.rabarbers.call.publish;

import com.rabarbers.call.domain.Domain;

public interface DomainPublisher {
    void publishTraceDetails(Domain domain, String path);
}

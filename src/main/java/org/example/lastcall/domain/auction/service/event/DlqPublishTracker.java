package org.example.lastcall.domain.auction.service.event;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class DlqPublishTracker {
  private final Set<String> returnedCorrelationIds = ConcurrentHashMap.newKeySet();
  public Set<String> getReturnedCorrelationIds() { return returnedCorrelationIds; }
}

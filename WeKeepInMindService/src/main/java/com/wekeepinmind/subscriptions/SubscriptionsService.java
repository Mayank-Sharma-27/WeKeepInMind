package com.wekeepinmind.subscriptions;

import com.wekeepinmind.dao.subscriptions.Subscriptions;

import java.util.Optional;

public interface SubscriptionsService {

    void updateSubscriptionsAndGroup(Subscriptions subscriptions);

    Optional<Subscriptions> getSubscription(String groupId);

}

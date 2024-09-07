package com.wekeepinmind.dao.subscriptions;

import java.util.Optional;

public interface SubscriptionsDAO {

    void saveSubscriptions(Subscriptions subscriptions);

    Optional<Subscriptions> getSubscription(String groupId);

}

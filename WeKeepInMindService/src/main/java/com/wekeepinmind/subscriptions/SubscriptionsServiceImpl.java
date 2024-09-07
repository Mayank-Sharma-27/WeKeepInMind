package com.wekeepinmind.subscriptions;

import com.wekeepinmind.dao.group.Group;
import com.wekeepinmind.dao.group.GroupDAO;
import com.wekeepinmind.dao.subscriptions.Subscriptions;
import com.wekeepinmind.dao.subscriptions.SubscriptionsDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionsServiceImpl implements SubscriptionsService {

    private final GroupDAO groupDAO;

    private final SubscriptionsDAO subscriptionsDAO;

    @Override
    public void updateSubscriptionsAndGroup(Subscriptions subscriptions) {
        Optional<Group> group = groupDAO.getGroupByGroupId(subscriptions.getGroupId());
        if (group.isEmpty()) {
            return;
        }

        if (subscriptions.isTrialVersion()) {
            group.get().setActive(true);
        }

        group.get().setActive(subscriptions.isActive());
        groupDAO.saveGroup(group.get());

    }

    @Override
    public Optional<Subscriptions> getSubscription(String groupId) {
        return Optional.empty();
    }
}

package com.wekeepinmind.controller.subscriptions;

import com.wekeepinmind.dao.subscriptions.Subscriptions;
import com.wekeepinmind.subscriptions.SubscriptionsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class SubscriptionsController {

    private final SubscriptionsService subscriptionsService;

    @PostMapping(value = "/create-subscription")
    public void createSubscription(@RequestBody CreateSubscriptionRequest request) {

        Subscriptions subscriptions = new Subscriptions(
                request.getGroupId(),
                request.isTrialVersion(),
                request.getExpirationTime(),
                request.getSubscriptionStartTime(),
                Subscriptions.Frequency.valueOf(request.getFrequency()),
                request.getSubscriptionValue(),
                true);

        subscriptionsService.updateSubscriptionsAndGroup(subscriptions);

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateSubscriptionRequest {
        private String groupId;
        private boolean isTrialVersion;
        private LocalDateTime expirationTime;
        private LocalDateTime subscriptionStartTime;
        private String frequency;
        private int subscriptionValue;
    }

}

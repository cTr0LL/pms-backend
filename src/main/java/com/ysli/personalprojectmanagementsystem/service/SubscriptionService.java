package com.ysli.personalprojectmanagementsystem.service;


import com.ysli.personalprojectmanagementsystem.modal.PlanType;
import com.ysli.personalprojectmanagementsystem.modal.Subscription;
import com.ysli.personalprojectmanagementsystem.modal.User;

public interface SubscriptionService {

    Subscription createSubscription(User user);

    Subscription getUserSubscription(Long userId) throws Exception;

    Subscription upgradeSubscription(Long userId, PlanType planType);

    boolean isValid(Subscription subscription);

}

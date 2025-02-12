package com.ysli.personalprojectmanagementsystem.repository;

import com.ysli.personalprojectmanagementsystem.modal.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Subscription findByUserId(Long userId);

}

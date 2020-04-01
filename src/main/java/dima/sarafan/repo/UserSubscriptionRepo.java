package dima.sarafan.repo;

import dima.sarafan.domain.User;
import dima.sarafan.domain.UserSubscription;
import dima.sarafan.domain.UserSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSubscriptionRepo extends JpaRepository<UserSubscription, UserSubscriptionId> {

    List<UserSubscription> findBySubscriber(User user);

}
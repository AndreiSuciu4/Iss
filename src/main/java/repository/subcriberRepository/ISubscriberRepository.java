package repository.subcriberRepository;

import domain.Subscriber;
import repository.Repository;

public interface ISubscriberRepository extends Repository<Integer, Subscriber> {
    Subscriber findByUsernameAndPassword(String username, String password);

}

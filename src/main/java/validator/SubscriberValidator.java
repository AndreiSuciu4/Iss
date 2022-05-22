package validator;

import domain.Subscriber;

public class SubscriberValidator {
    public void validate(Subscriber subscriber) throws ValidatorException {
        String errors = "";
        if(subscriber.getFirstName() == "")
            errors += "invalid firstName!\n";
        if(subscriber.getLastName() == "")
            errors += "invalid lastName!\n";
        if(subscriber.getCnp().length() != 13)
            errors += "invalid CNP!\n";
        if(subscriber.getPhoneNo().length() != 10)
                errors += "invalid phoneNumber!\n";
        if(subscriber.getPassword() == "")
            errors += "invalid password!\n";
        if(errors.length() != 0)
            throw new ValidatorException(errors);

    }
}

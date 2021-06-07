package TripPricer.proxy;

import TripPricer.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This interface allows to send requests to the user api
 */
@FeignClient(name = "user-api", url = "localhost:8080/user")
public interface UserProxy {

    /**
     * Send the user getting request
     * @param userName : Name of the User to found
     * @return The User found (JSon)
     */
    @GetMapping(value = "/get")
    public User getUser(String userName);
}

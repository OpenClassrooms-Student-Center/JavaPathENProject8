package TripPricer.proxy;

import TripPricer.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-api", url = "localhost:8080/user")
public interface UserProxy {

    @GetMapping(value = "/get")
    public User getUser(String userName);
}

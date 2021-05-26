package reward.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import reward.model.User;

@FeignClient(name = "user-api", url = "localhost:8080/user")
public interface UserProxy {

    @GetMapping(value = "/get")
    public User getUser(String userName);
}

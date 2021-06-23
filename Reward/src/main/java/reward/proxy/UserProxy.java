package reward.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reward.model.User;
import reward.model.UserReward;

import java.text.ParseException;

/**
 * This interface allows to send requests to the user api
 */
@FeignClient(name = "user-api", url = "localhost:8080")
public interface UserProxy {

    /**
     * Send the user getting request
     * @param userName : Name of the User to found
     * @return The User found
     */
    @GetMapping(value = "/getUser", produces = "application/json")
    public User getUser(@RequestParam String userName);


    @PostMapping(value = "/addUserReward", produces = "application/json")
    public void addUserReward(@RequestParam String userName, @RequestBody UserReward userReward);

}

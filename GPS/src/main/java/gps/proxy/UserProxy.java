package gps.proxy;

import gps.model.User;
import gpsUtil.location.VisitedLocation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "user-api", url = "localhost:8080")
public interface UserProxy {

    @GetMapping(value = "/getUser", produces = "application/json")
    public User getUser(@RequestParam String userName);

    @GetMapping(value = "/getAllUser", produces = "application/json")
    public List<User> getAllUser();

    @PostMapping(value = "/addToVisitedLocations", produces = "application/json")
    public void addToVisitedLocations(@RequestParam String userName, @RequestParam VisitedLocation visitedLocation);
}

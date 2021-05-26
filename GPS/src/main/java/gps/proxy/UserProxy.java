package gps.proxy;

import gps.model.User;
import gpsUtil.location.VisitedLocation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "user-api", url = "localhost:8080")
public interface UserProxy {

    @GetMapping(value = "/getUser")
    public User getUser(String userName);

    @GetMapping(value = "/getAllUser")
    public List<User> getAllUser();

    @PostMapping(value = "/addToVisitedLocations")
    public void addToVisitedLocations(String userName, VisitedLocation visitedLocation);
}

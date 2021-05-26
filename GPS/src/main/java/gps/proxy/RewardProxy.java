package gps.proxy;

import gps.model.User;
import gpsUtil.location.VisitedLocation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "reward-api", url = "localhost:8082")
public interface RewardProxy {

    @PostMapping(value = "/calculateRewards")
    public void calculateRewards(String userName);
}

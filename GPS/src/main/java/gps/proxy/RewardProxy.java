package gps.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "reward-api", url = "localhost:8082")
public interface RewardProxy {

    @GetMapping(value = "/getRewardPoints")
    public int getRewardPoints(String attractionName, String userName);

    @PostMapping(value = "/calculateRewards")
    public void calculateRewards(String userName);
}

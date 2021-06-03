package gps.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "reward-api", url = "localhost:8082")
public interface RewardProxy {

    @GetMapping(value = "/getRewardPoints", produces = "application/json")
    public int getRewardPoints(@RequestParam String attractionName, @RequestParam String userName);

    @PostMapping(value = "/calculateRewards", produces = "application/json")
    public void calculateRewards(@RequestParam String userName);
}

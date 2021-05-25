package reward.controller;

import com.jsoniter.output.JsonStream;
import gpsUtil.location.Attraction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reward.model.User;
import reward.proxy.GpsProxy;
import reward.proxy.UserProxy;
import reward.service.RewardService;
import reward.service.RewardServiceInterface;

import java.util.List;

@RestController
public class RewardController {

    private RewardServiceInterface rewardServiceInterface;

    private UserProxy userProxy;
    private GpsProxy gpsProxy;

    public RewardController() {
        rewardServiceInterface = new RewardService();
    }

    public RewardController(RewardServiceInterface rewardServiceInterface) {
        this.rewardServiceInterface = rewardServiceInterface;
    }

    @RequestMapping("/getRewards")
    public String getRewards(@RequestParam String userName) {

        User user = userProxy.getUser(userName);

        return JsonStream.serialize(rewardServiceInterface.getUserRewards(user));
    }

    @RequestMapping("/calculateRewards")
    public String calculateRewards(@RequestParam String userName) {

        User user = userProxy.getUser(userName);

        List<Attraction> attractionList = gpsProxy.getNearByAttractions();

        return JsonStream.serialize(rewardServiceInterface.calculateRewards(user, attractionList));
    }
}

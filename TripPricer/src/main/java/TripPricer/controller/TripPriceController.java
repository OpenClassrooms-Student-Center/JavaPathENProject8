package TripPricer.controller;

import TripPricer.model.User;
import TripPricer.proxy.UserProxy;
import TripPricer.service.TripPricerService;
import TripPricer.service.TripPricerServiceInterface;
import com.jsoniter.output.JsonStream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TripPriceController {

    private TripPricerServiceInterface tripPricerServiceInterface;

    private UserProxy userProxy;

    public TripPriceController() {
        tripPricerServiceInterface = new TripPricerService();
    }

    public TripPriceController(TripPricerServiceInterface tripPricerServiceInterface) {
        this.tripPricerServiceInterface = tripPricerServiceInterface;
    }

    @RequestMapping("/getTripDeals")
    public String getTripDeals(@RequestParam String userName) {

        User user = userProxy.getUser(userName);

        return JsonStream.serialize(tripPricerServiceInterface.getTripDeals(user));
    }
}

package TripPricer.controller;

import TripPricer.model.User;
import TripPricer.proxy.UserProxy;
import TripPricer.service.TripPricerService;
import TripPricer.service.TripPricerServiceInterface;
import com.jsoniter.output.JsonStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TripPriceController {

    private Logger logger = LogManager.getLogger(getClass().getSimpleName());

    private TripPricerServiceInterface tripPricerServiceInterface;

    public TripPriceController() {
        logger.info("TripPriceController()");

        tripPricerServiceInterface = new TripPricerService();
    }

    public TripPriceController(TripPricerServiceInterface tripPricerServiceInterface) {
        logger.info("TripPriceController(" + tripPricerServiceInterface + ")");

        this.tripPricerServiceInterface = tripPricerServiceInterface;
    }

    @RequestMapping("/getTripDeals")
    public String getTripDeals(@RequestParam String userName) {
        logger.info("getTripDeals(" + userName + ")");

        return JsonStream.serialize(tripPricerServiceInterface.getTripDeals(userName));
    }
}

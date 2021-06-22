package reward.proxy;

import gpsUtil.location.Attraction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reward.model.UserNearestAttraction;

import java.util.List;

/**
 * This interface allows to send requests to the gps api
 */
@FeignClient(name = "gps-api", url = "localhost:8081")
public interface GpsProxy {

    /**
     * Send the attraction getting request
     * @param attractionName : Name of the attraction to found
     * @return The Attraction found
     */
    @GetMapping(value = "/getAttraction", produces = "application/json")
    public Attraction getAttraction(@RequestParam String attractionName);

    /**
     * Send the attraction list getting request
     * @return The Attraction list
     */
    @GetMapping(value = "/getAllAttraction", produces = "application/json")
    public List<Attraction> getAllAttraction();
}
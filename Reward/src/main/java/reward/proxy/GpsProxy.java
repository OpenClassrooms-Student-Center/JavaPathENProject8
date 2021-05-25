package reward.proxy;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "gps-api", url = "localhost:8080/gps")
public interface GpsProxy {

    @GetMapping(value = "/user")
    public VisitedLocation getUserLocation(@RequestParam UUID userId);

    @GetMapping(value = "/attractionList")
    public List<Attraction> getAttractionList();

    @GetMapping(value = "/nearAttraction")
    public List<Attraction> getNearByAttractions();
}

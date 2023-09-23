package com.openclassrooms.tourguide;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import rewardCentral.RewardCentral;
import com.openclassrooms.tourguide.helper.InternalTestHelper;
import com.openclassrooms.tourguide.service.RewardsService;
import com.openclassrooms.tourguide.service.TourGuideService;
import com.openclassrooms.tourguide.user.User;

public class TestPerformance {

	/*
	 * A note on performance improvements:
	 * 
	 * The number of users generated for the high volume tests can be easily
	 * adjusted via this method:
	 * 
	 * InternalTestHelper.setInternalUserNumber(100000);
	 * 
	 * 
	 * These tests can be modified to suit new solutions, just as long as the
	 * performance metrics at the end of the tests remains consistent.
	 * 
	 * These are performance metrics that we are trying to hit:
	 * 
	 * highVolumeTrackLocation: 100,000 users within 15 minutes:
	 * assertTrue(TimeUnit.MINUTES.toSeconds(15) >=
	 * TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	 *
	 * highVolumeGetRewards: 100,000 users within 20 minutes:
	 * assertTrue(TimeUnit.MINUTES.toSeconds(20) >=
	 * TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	 */

	@Test
	@Disabled
	@DisplayName("Traque la position de l'utilisateur sur un volume conséquent de données.")
	public void highVolumeTrackLocation() {
		GpsUtil gpsUtil = new GpsUtil();
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		// Users should be incremented up to 100,000, and test finishes within 15 minutes.
		InternalTestHelper.setInternalUserNumber(1000);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

		List<User> allUsers = new ArrayList<>();
		allUsers = tourGuideService.getAllUsers();

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		/*
		 * Étant donné que le traitement de chaque utilisateur est indépendant, 
		 * on peut utiliser la parallélisation pour traiter plusieurs utilisateurs à la fois.
		 * Ceci peut « skyrocket » les performances.
		 */
		
		allUsers.parallelStream().forEach(user -> tourGuideService.trackUserLocation(user));

		/*
		 * Attention avec parallelStream() : Elle peut introduire un conflit, surtout si le nombre de threads utilisés est trop élevé par rapport au nombre de coeurs CPU dispo.
		 */
		
		// Note :
		// On pourait peut être utiliser une liste (List<CompletableFuture<Void>> futures = new ArrayList<>();).
		// CompletableFuture pourrait être utile dans notre contexte pour traiter les utilisateurs.
		
		stopWatch.stop();
		tourGuideService.tracker.stopTracking();

		System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}

	@Test
	@DisplayName("Récupère les récompenses des utilisateurs sur un volume conséquent de données.")
	public void highVolumeGetRewards() {
		GpsUtil gpsUtil = new GpsUtil();
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());

		// Users should be incremented up to 100,000, and test finishes within 20 minutes.
		InternalTestHelper.setInternalUserNumber(1000);
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

		Attraction attraction = gpsUtil.getAttractions().get(0);
		
		List<User> allUsers = tourGuideService.getAllUsers();

        /*
		// Convertir en CopyOnWriteArrayList pour éviter ConcurrentModificationException par la suite.
		CopyOnWriteArrayList<User> concurrentSafeUsers = new CopyOnWriteArrayList<>(allUsers);
		
		// Utilisation de CompletableFuture pour l'exécution parallèle.
		List<CompletableFuture<Void>> futures = concurrentSafeUsers.stream().map(u -> CompletableFuture.runAsync(() -> {
			u.addToVisitedLocations(new VisitedLocation(u.getUserId(), att raction, new Date()));
			rewardsService.calculateRewards(u);
		})).collect(Collectors.toList());
		
		// Attendre que tous les CompletableFutures soient terminés.
		futures.forEach(CompletableFuture::join);
        */

        allUsers.forEach(u -> u.addToVisitedLocations(new VisitedLocation(u.getUserId(), attraction, new Date())));

		allUsers.forEach(u -> rewardsService.calculateRewards(u));

		for (User user : allUsers) {
			assertTrue(user.getUserRewards().size() > 0);
		}
		
		stopWatch.stop();
		tourGuideService.tracker.stopTracking();

		System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
		assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}

}

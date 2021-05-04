package tourGuide;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Ignore;
import org.junit.Test;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import rewardCentral.RewardCentral;
import tourGuide.helper.InternalTestHelper;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;
import tripPricer.Provider;

public class TestTourGuideService {

	@Test
	public void getUserLocation() throws InterruptedException, ExecutionException {
		GpsUtil gpsUtil = new GpsUtil();
		ExecutorService executorService = Executors.newFixedThreadPool(1000);
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral(), executorService);
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService, executorService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user).get();
		tourGuideService.tracker.stopTracking();
		assertTrue(visitedLocation.userId.equals(user.getUserId()));
	}
	
	@Test
	public void addUser() {
		GpsUtil gpsUtil = new GpsUtil();
		ExecutorService executorService = Executors.newFixedThreadPool(1000);
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral(), executorService);
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService, executorService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);
		
		User retrivedUser = tourGuideService.getUser(user.getUserName());
		User retrivedUser2 = tourGuideService.getUser(user2.getUserName());

		tourGuideService.tracker.stopTracking();
		
		assertEquals(user, retrivedUser);
		assertEquals(user2, retrivedUser2);
	}
	
	@Test
	public void getAllUsers() {
		GpsUtil gpsUtil = new GpsUtil();
		ExecutorService executorService = Executors.newFixedThreadPool(1000);
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral(), executorService);
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService, executorService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);
		
		List<User> allUsers = tourGuideService.getAllUsers();

		tourGuideService.tracker.stopTracking();
		
		assertTrue(allUsers.contains(user));
		assertTrue(allUsers.contains(user2));
	}
	
	@Test
	public void trackUser() throws InterruptedException, ExecutionException {
		GpsUtil gpsUtil = new GpsUtil();
		ExecutorService executorService = Executors.newFixedThreadPool(1000);
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral(), executorService);
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService, executorService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user).get();
		
		tourGuideService.tracker.stopTracking();
		
		assertEquals(user.getUserId(), visitedLocation.userId);
	}
	
	@Ignore // Not yet implemented
	@Test
	public void getNearbyAttractions() throws InterruptedException, ExecutionException {
		GpsUtil gpsUtil = new GpsUtil();
		ExecutorService executorService = Executors.newFixedThreadPool(1000);
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral(), executorService);
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService, executorService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user).get();
		
		List<Attraction> attractions = tourGuideService.getNearByAttractions(visitedLocation);
		
		tourGuideService.tracker.stopTracking();
		
		assertEquals(5, attractions.size());
	}
	
	@Test
	public void getClosestAttractions_shouldReturn5ClosestAttraction() throws InterruptedException, ExecutionException {
		GpsUtil gpsUtil = new GpsUtil();
		ExecutorService executorService = Executors.newFixedThreadPool(1000);
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral(), executorService);
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService, executorService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		VisitedLocation userLocation = tourGuideService.trackUserLocation(user).get();
		
		Map<Double, Attraction> attractions = tourGuideService.getClosestAttractions(userLocation, 5);
		
		attractions.forEach((k, v) -> {
			System.out.printf("Attraction: %s, distance: %f\n", v.attractionName, k.doubleValue());
		});
		
		tourGuideService.tracker.stopTracking();
		
		assertEquals(5, attractions.size());
	}
	
	public void getTripDeals() {
		GpsUtil gpsUtil = new GpsUtil();
		ExecutorService executorService = Executors.newFixedThreadPool(1000);
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral(), executorService);
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService, executorService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

		List<Provider> providers = tourGuideService.getTripDeals(user);
		
		tourGuideService.tracker.stopTracking();
		
		assertEquals(10, providers.size());
	}
	
	
}

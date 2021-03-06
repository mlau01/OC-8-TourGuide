package tourGuide;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

//Can only be used when RewardCentral microservice is UP
public class TestProxyRewardCentral {
	
	
	private tourGuide.proxy.rewardcentral.RewardCentral rewardCentral;
	
	@Before
	public void init() {
		rewardCentral = Feign.builder()
				.encoder(new GsonEncoder())
				.decoder(new GsonDecoder())
				.target(tourGuide.proxy.rewardcentral.RewardCentral.class, TestProperties.rewardCentralSocket);
	}
	
	@Test
	public void rewardCentralProxyGetAttractionRewardPoints_shouldReturnAnAttractionRewardPointsAsJsonString() {
		assertNotEquals(0, rewardCentral.getAttractionRewardPoints(UUID.randomUUID(), UUID.randomUUID()));
	}

}

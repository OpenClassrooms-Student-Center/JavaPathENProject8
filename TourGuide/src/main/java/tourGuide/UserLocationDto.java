package tourGuide;

import java.util.UUID;

public class UserLocationDto {
	
	private UUID userId;
	
	private LocationDto location;

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public LocationDto getLocation() {
		return location;
	}

	public void setLocation(LocationDto location) {
		this.location = location;
	}

	public UserLocationDto(UUID userId, LocationDto location) {
		super();
		this.userId = userId;
		this.location = location;
	}

	public UserLocationDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return  userId + location.toString();
	}
	
	

}

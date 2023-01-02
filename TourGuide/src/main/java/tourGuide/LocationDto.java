package tourGuide;

public class LocationDto {

	private double longitude;

	private double latitude;

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public LocationDto(double longitude, double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public LocationDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return " { longitude:" + longitude + ", latitude=" + latitude + "}";
	}

}

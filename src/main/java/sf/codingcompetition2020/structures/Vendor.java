package sf.codingcompetition2020.structures;

import java.util.List;

public class Vendor {
	private int vendorId;
	private String area;
	private int vendorRating;
	private boolean inScope;

	public Vendor(int vendorId, String area, int vendorRating, boolean inScope) {
		this.vendorId = vendorId;
		this.area = area;
		this.vendorRating = vendorRating;
		this.inScope = inScope;
	}

	public Vendor(List<String> s){
		this(Integer.parseInt(s.get(0)), s.get(1), Integer.parseInt(s.get(2)), Boolean.parseBoolean(s.get(3)));
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getVendorRating() {
		return vendorRating;
	}

	public void setVendorRating(int vendorRating) {
		this.vendorRating = vendorRating;
	}

	public boolean isInScope() {
		return inScope;
	}

	public void setInScope(boolean inScope) {
		this.inScope = inScope;
	}
}

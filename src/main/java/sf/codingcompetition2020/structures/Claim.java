package sf.codingcompetition2020.structures;

import java.util.List;

public class Claim {
	private int claimId;
	private int customerId;
	private boolean closed;
	private int monthsOpen;

	public Claim(int claimId, int customerId, boolean closed, int monthsOpen) {
		this.claimId = claimId;
		this.customerId = customerId;
		this.closed = closed;
		this.monthsOpen = monthsOpen;

	}

	public Claim(List<String> stringList) {
		this(Integer.parseInt(stringList.get(0)), Integer.parseInt(stringList.get(1)), Boolean.parseBoolean(stringList.get(2)),
				Integer.parseInt(stringList.get(3)));
	}


	public int getClaimId() {
		return claimId;
	}

	public void setClaimId(int claimId) {
		this.claimId = claimId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public int getMonthsOpen() {
		return monthsOpen;
	}

	public void setMonthsOpen(int monthsOpen) {
		this.monthsOpen = monthsOpen;
	}
}

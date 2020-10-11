package sf.codingcompetition2020.structures;

import java.util.List;

public class Agent {
	
	private int agentId;
	private String area;
	private String language;
	private String firstName;
	private String lastName;

	public Agent(int agentId, String area, String language, String firstName, String lastName){
		this.agentId = agentId;
		this.area = area;
		this.language = language;

		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Agent(List<String> list){
		this(Integer.parseInt(list.get(0)), list.get(1), list.get(2), list.get(3), list.get(4));
	}

	public String getFirstName() {
		return firstName;
	}

	public int getAgentId() {
		return agentId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}

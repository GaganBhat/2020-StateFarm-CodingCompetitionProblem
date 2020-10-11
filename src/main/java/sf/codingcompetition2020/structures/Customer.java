package sf.codingcompetition2020.structures;

import com.fasterxml.jackson.databind.node.BooleanNode;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Customer {
	private int customerId;
	private String firstName;
	private String lastName;
	private int age;
	private String area;
	private int agentId;
	private short agentRating;
	private String primaryLanguage;
	private List<Dependent> dependents;
	private boolean homePolicy;
	private boolean autoPolicy;
	private boolean rentersPolicy;
	private String totalMonthlyPremium;
	private short yearsOfService;
	private Integer vehiclesInsured;

	public Customer(int customerId, String firstName, String lastName, int age, String area, int agentId,
					short agentRating, String primaryLanguage, String dependents, boolean homePolicy,
					boolean autoPolicy, boolean rentersPolicy, String totalMonthlyPremium, short yearsOfService, Integer vehiclesInsured){
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.area = area;
		this.agentId = agentId;
		this.agentRating = agentRating;
		this.primaryLanguage = primaryLanguage;
		this.homePolicy = homePolicy;
		this.autoPolicy = autoPolicy;
		this.rentersPolicy = rentersPolicy;
		this.totalMonthlyPremium = totalMonthlyPremium;
		this.yearsOfService = yearsOfService;
		this.vehiclesInsured = vehiclesInsured;

		if(dependents.length() == 0)
			this.dependents = new ArrayList<>();
		else {
			dependents = dependents.substring(1, dependents.length() - 1 );
			String jsonStr = (dependents.replace("\"\"", "\""));
			Gson gson = new Gson();
			this.dependents = Arrays.asList(gson.fromJson(jsonStr, Dependent[].class));
		}
	}

	public Customer(List<String> stringList) {
		this(Integer.parseInt(stringList.get(0)), stringList.get(1), stringList.get(2), Integer.parseInt(stringList.get(3)), stringList.get(4), Integer.parseInt(stringList.get(5)),
				Short.parseShort(stringList.get(6)), stringList.get(7), stringList.get(8), Boolean.parseBoolean(stringList.get(9)),
				Boolean.parseBoolean(stringList.get(10)), Boolean.parseBoolean(stringList.get(11)), stringList.get(12), Short.parseShort(stringList.get(13)),
				Integer.parseInt(stringList.get(14)));
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	public short getAgentRating() {
		return agentRating;
	}

	public void setAgentRating(short agentRating) {
		this.agentRating = agentRating;
	}

	public String getPrimaryLanguage() {
		return primaryLanguage;
	}

	public void setPrimaryLanguage(String primaryLanguage) {
		this.primaryLanguage = primaryLanguage;
	}

	public List<Dependent> getDependents() {
		return dependents;
	}

	public void setDependents(List<Dependent> dependents) {
		this.dependents = dependents;
	}

	public boolean isHomePolicy() {
		return homePolicy;
	}

	public void setHomePolicy(boolean homePolicy) {
		this.homePolicy = homePolicy;
	}

	public boolean isAutoPolicy() {
		return autoPolicy;
	}

	public void setAutoPolicy(boolean autoPolicy) {
		this.autoPolicy = autoPolicy;
	}

	public boolean isRentersPolicy() {
		return rentersPolicy;
	}

	public void setRentersPolicy(boolean rentersPolicy) {
		this.rentersPolicy = rentersPolicy;
	}

	public String getTotalMonthlyPremium() {
		return totalMonthlyPremium;
	}

	public Integer getTotalMonthlyPremiumString() {
		return Integer.parseInt(this.getTotalMonthlyPremium().substring(1));
	}

	public void setTotalMonthlyPremium(String totalMonthlyPremium) {
		this.totalMonthlyPremium = totalMonthlyPremium;
	}

	public short getYearsOfService() {
		return yearsOfService;
	}

	public void setYearsOfService(short yearsOfService) {
		this.yearsOfService = yearsOfService;
	}

	public Integer getVehiclesInsured() {
		return vehiclesInsured;
	}

	public void setVehiclesInsured(Integer vehiclesInsured) {
		this.vehiclesInsured = vehiclesInsured;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"customerId=" + customerId +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", age=" + age +
				", area='" + area + '\'' +
				", agentId=" + agentId +
				", agentRating=" + agentRating +
				", primaryLanguage='" + primaryLanguage + '\'' +
				", dependents=" + dependents +
				", homePolicy=" + homePolicy +
				", autoPolicy=" + autoPolicy +
				", rentersPolicy=" + rentersPolicy +
				", totalMonthlyPremium='" + totalMonthlyPremium + '\'' +
				", yearsOfService=" + yearsOfService +
				", vehiclesInsured=" + vehiclesInsured +
				'}';
	}
}
package sf.codingcompetition2020;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.server.ExportException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import sf.codingcompetition2020.structures.Agent;
import sf.codingcompetition2020.structures.Claim;
import sf.codingcompetition2020.structures.Customer;
import sf.codingcompetition2020.structures.Vendor;

public class CodingCompCsvUtil {
	
	/* #1 
	 * readCsvFile() -- Read in a CSV File and return a list of entries in that file.
	 * @param filePath -- Path to file being read in.
	 * @param classType -- Class of entries being read in.
	 * @return -- List of entries being returned.
	 */
	public <T> List<T> readCsvFile(String filePath, Class<T> classType) {
		List<T> list = new ArrayList<>();
		try {
			csvStreamer(filePath).skip(1).forEach(stringList -> {
//				if(classType == Agent.class)
//					list.add((T) new Agent(stringList));
//				if(classType == Claim.class)
//					list.add((T) new Claim(stringList));
//				if(classType == Customer.class)
//					list.add((T) new Customer(stringList));
				try {
					list.add(classType.getConstructor(List.class).newInstance(stringList));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	
	/* #2
	 * getAgentCountInArea() -- Return the number of agents in a given area.
	 * @param filePath -- Path to file being read in.
	 * @param area -- The area from which the agents should be counted.
	 * @return -- The number of agents in a given area
	 */
	public int getAgentCountInArea(String filePath,String area) {
		return (int) readCsvFile(filePath, Agent.class).stream()
				.filter(ag -> ag.getArea().equals(area)).count();
	}

	
	/* #3
	 * getAgentsInAreaThatSpeakLanguage() -- Return a list of agents from a given area, that speak a certain language.
	 * @param filePath -- Path to file being read in.
	 * @param area -- The area from which the agents should be counted.
	 * @param language -- The language spoken by the agent(s).
	 * @return -- The number of agents in a given area
	 */
	public List<Agent> getAgentsInAreaThatSpeakLanguage(String filePath, String area, String language) {
		return readCsvFile(filePath, Agent.class).stream()
				.filter(a -> a.getLanguage().equals(language) && a.getArea().equals(area))
				.collect(Collectors.toList());
	}
	
	
	/* #4
	 * countCustomersFromAreaThatUseAgent() -- Return the number of individuals from an area that use a certain agent.
	 * @param filePath -- Path to file being read in.
	 * @param customerArea -- The area from which the customers should be counted.
	 * @param agentFirstName -- First name of agent.
	 * @param agentLastName -- Last name of agent.
	 * @return -- The number of customers that use a certain agent in a given area.
	 */
	public short countCustomersFromAreaThatUseAgent(Map<String,String> csvFilePaths, String customerArea, String agentFirstName, String agentLastName) {

		Object[] f = readCsvFile(csvFilePaths.get("agentList"), Agent.class).stream()
				.filter(agent -> agent.getFirstName().equals(agentFirstName) && agent.getLastName().equals(agentLastName)).toArray();

		return (short) (readCsvFile(csvFilePaths.get("customerList"), Customer.class).stream()
				.filter(customer -> customer.getArea().equals(customerArea)
						&& customer.getAgentId() == ((Agent) f [0]).getAgentId()).count());
	}

	
	/* #5
	 * getCustomersRetainedForYearsByPlcyCostAsc() -- Return a list of customers retained for a given number of years, in ascending order of their policy cost.
	 * @param filePath -- Path to file being read in.
	 * @param yearsOfServeice -- Number of years the person has been a customer.
	 * @return -- List of customers retained for a given number of years, in ascending order of policy cost.
	 */
	public List<Customer> getCustomersRetainedForYearsByPlcyCostAsc(String customerFilePath, short yearsOfService) {
		List<Customer> custList = readCsvFile(customerFilePath, Customer.class);
		List<Customer> filterCustomer = new ArrayList<>();

		custList.forEach(current -> {
			if (current.getYearsOfService() == yearsOfService) {
				filterCustomer.add(current);
			}
		});

		Collections.sort(filterCustomer, Comparator.comparingInt(Customer::getTotalMonthlyPremiumString));
		return filterCustomer;
	}

	
	/* #6
	 * getLeadsForInsurance() -- Return a list of individuals who’ve made an inquiry for a policy but have not signed up.
	 * *HINT* -- Look for customers that currently have no policies with the insurance company.
	 * @param filePath -- Path to file being read in.
	 * @return -- List of customers who’ve made an inquiry for a policy but have not signed up.
	 */
	public List<Customer> getLeadsForInsurance(String filePath) {
		List<Customer> custList = readCsvFile(filePath, Customer.class);
		List<Customer> filterCustomer = new ArrayList<>();
		for (Customer current : custList) {
			if (!current.isAutoPolicy() && !current.isHomePolicy() && !current.isRentersPolicy())
				filterCustomer.add(current);

		}
		return filterCustomer;
	}


	/* #7
	 * getVendorsWithGivenRatingThatAreInScope() -- Return a list of vendors within an area and include options to narrow it down by: 
			a.	Vendor rating
			b.	Whether that vendor is in scope of the insurance (if inScope == false, return all vendors in OR out of scope, if inScope == true, return ONLY vendors in scope)
	 * @param filePath -- Path to file being read in.
	 * @param area -- Area of the vendor.
	 * @param inScope -- Whether or not the vendor is in scope of the insurance.
	 * @param vendorRating -- The rating of the vendor.
	 * @return -- List of vendors within a given area, filtered by scope and vendor rating.
	 */
	public List<Vendor> getVendorsWithGivenRatingThatAreInScope(String filePath, String area, boolean inScope, int vendorRating) {
		 List<Vendor> vendors = new ArrayList<>();
		 readCsvFile(filePath, Vendor.class).stream().forEach(vendor -> {
			if( (!inScope || vendor.isInScope()) && vendor.getArea().equals(area) && vendor.getVendorRating() >= vendorRating)
				vendors.add(vendor);
		});

		 return vendors;
	}


	/* #8
	 * getUndisclosedDrivers() -- Return a list of customers between the age of 40 and 50 years (inclusive), who have:
			a.	More than X cars
			b.	less than or equal to X number of dependents.
	 * @param filePath -- Path to file being read in.
	 * @param vehiclesInsured -- The number of vehicles insured.
	 * @param dependents -- The number of dependents on the insurance policy.
	 * @return -- List of customers filtered by age, number of vehicles insured and the number of dependents.
	 */
	public List<Customer> getUndisclosedDrivers(String filePath, int vehiclesInsured, int dependents) {
		List<Customer> cust = readCsvFile(filePath, Customer.class);

		List<Customer> customCust = new ArrayList<>();
		for (Customer current : cust)
			if (current.getAge() >= 40 && current.getAge() <= 50)
				if (current.getVehiclesInsured() > vehiclesInsured && current.getDependents().size() <= dependents)
					customCust.add(current);
		return customCust;
	}


	/* #9
	 * getAgentIdGivenRank() -- Return the agent with the given rank based on average customer satisfaction rating. 
	 * *HINT* -- Rating is calculated by taking all the agent rating by customers (1-5 scale) and dividing by the total number 
	 * of reviews for the agent.
	 * @param filePath -- Path to file being read in.
	 * @param agentRank -- The rank of the agent being requested.
	 * @return -- Agent ID of agent with the given rank.
	 */
	public int getAgentIdGivenRank(String filePath, int agentRank) {


		List<Customer> custList = readCsvFile(filePath, Customer.class);

		Map<Integer, Double> sumOfAgentRatings = new HashMap<>();
		Map<Integer, Integer> countOfAgentRatings = new HashMap<>();

		custList.forEach(customer -> {
			int id = customer.getAgentId();
			double currentSumOfAgentRatings = 0;
			int currentCountOfAgentRatings = 0;

			if(sumOfAgentRatings.get(id) != null)
				currentSumOfAgentRatings = sumOfAgentRatings.get(id);
			if(countOfAgentRatings.get(id) != null)
				currentCountOfAgentRatings = countOfAgentRatings.get(id);

			double newSumOfAgentRatings = currentSumOfAgentRatings + (double) customer.getAgentRating();
			int newCountOfAgentRatings = currentCountOfAgentRatings + 1;

			sumOfAgentRatings.put(id, newSumOfAgentRatings);
			countOfAgentRatings.put(id, newCountOfAgentRatings);
		});

		Map<Integer, Double> ratingCumulativeAverage = new HashMap<>();
		for (Integer agentId: sumOfAgentRatings.keySet()) {
			ratingCumulativeAverage.put(agentId, sumOfAgentRatings.get(agentId) / countOfAgentRatings.get(agentId));
		}

		List<Integer> sortedAgents = ratingCumulativeAverage.entrySet().stream()
				.sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());

		return sortedAgents.get(agentRank-1);
	}

	
	/* #10
	 * getCustomersWithClaims() -- Return a list of customers who’ve filed a claim within the last <numberOfMonths> (inclusive). 
	 * @param filePath -- Path to file being read in.
	 * @param monthsOpen -- Number of months a policy has been open.
	 * @return -- List of customers who’ve filed a claim within the last <numberOfMonths>.
	 */
	public List<Customer> getCustomersWithClaims(Map<String,String> csvFilePaths, short monthsOpen) {
		String customerListFilePath = csvFilePaths.get("customerList");
		String claimsListFilePath = csvFilePaths.get("claimList");
		List<Customer> customerList = readCsvFile(customerListFilePath, Customer.class);
		List<Claim> claimsList = readCsvFile(claimsListFilePath, Claim.class);

		Set<Customer> customersWithClaims = new HashSet<>();

		claimsList.forEach(currClaim -> {
			if (currClaim.getMonthsOpen() <= monthsOpen) {
				int currentClaimCustomerId = currClaim.getCustomerId();
				for(Customer cust : customerList)
					if(cust.getCustomerId() == currentClaimCustomerId)
						customersWithClaims.add(customerList.get(currentClaimCustomerId-1));

			}
		});
		ArrayList list = new ArrayList<>();
		list.addAll(customersWithClaims);
		list.remove(0);
		return list;
	}

	private Stream<List<String>> csvStreamer(String fileName) throws IOException {
		return Files.lines(Paths.get(fileName)).map(line -> {
			String[] lineContents = line.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
			return Arrays.asList(lineContents);
		});
	}

}

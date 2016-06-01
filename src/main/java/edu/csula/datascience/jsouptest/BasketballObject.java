package edu.csula.datascience.jsouptest;

import java.util.Calendar;
import java.util.Date;

public class BasketballObject {

	private String team;
	private Integer year;
	private boolean postseason;
	private double games_played;
	private double points_per_game;
	private double field_goal_made;
	private double field_goal_attempted;
	private double field_goal_percentage;
	private double two_points_made;
	private double two_points_attempted;
	private double two_points_percentage;
	private double three_points_made;
	private double three_points_attempted;
	private double three_points_percentage;
	private double free_throws_made;
	private double free_throws_attempted;
	private double free_throws_percentage;
	private double offensive_rebounds;
	private double defensive_rebounds;
	private double total_rebounds;
	private double assists;
	private double steals;
	private double blocks;
	private double turnovers;
	private double fouls;
	private double def_points_per_game;
	private double point_difference;
	private double def_field_goal_percentage;
	private double def_three_points_percentage;
	private double offensive_rebounds_percentage;
	private double defensive_rebounds_percentage;
	private double total_rebounds_percentage;
	private double def_turnovers;
	private String name;
	private double minutes_played;
	private Date year_date;
	private String city;

	public BasketballObject() {
	}

	public BasketballObject(String team, Integer year, boolean postseason, double games_played, double points_per_game,
			double field_goal_made, double field_goal_attempted, double field_goal_percentage, double two_points_made,
			double two_points_attempted, double two_points_percentage, double three_points_made,
			double three_points_attempted, double three_points_percentage, double free_throws_made,
			double free_throws_attempted, double free_throws_percentage, double offensive_rebounds,
			double defensive_rebounds, double total_rebounds, double assists, double steals, double blocks,
			double turnovers, double fouls, double def_points_per_game, double point_difference,
			double def_field_goal_percentage, double def_three_points_percentage, double offensive_rebounds_percentage,
			double defensive_rebounds_percentage, double total_rebounds_percentage, double def_turnovers) {

		this.team = team;
		this.year = year;
		this.postseason = postseason;
		this.games_played = games_played;
		this.points_per_game = points_per_game;
		this.field_goal_made = field_goal_made;
		this.field_goal_attempted = field_goal_attempted;
		this.field_goal_percentage = field_goal_percentage;
		this.two_points_made = two_points_made;
		this.two_points_attempted = two_points_attempted;
		this.two_points_percentage = two_points_percentage;
		this.three_points_made = three_points_made;
		this.three_points_attempted = three_points_attempted;
		this.three_points_percentage = three_points_percentage;
		this.free_throws_made = free_throws_made;
		this.free_throws_attempted = free_throws_attempted;
		this.free_throws_percentage = free_throws_percentage;
		this.offensive_rebounds = offensive_rebounds;
		this.defensive_rebounds = defensive_rebounds;
		this.total_rebounds = total_rebounds;
		this.assists = assists;
		this.steals = steals;
		this.blocks = blocks;
		this.turnovers = turnovers;
		this.fouls = fouls;
		this.def_points_per_game = def_points_per_game;
		this.point_difference = point_difference;
		this.def_field_goal_percentage = def_field_goal_percentage;
		this.def_three_points_percentage = def_three_points_percentage;
		this.offensive_rebounds_percentage = offensive_rebounds_percentage;
		this.defensive_rebounds_percentage = defensive_rebounds_percentage;
		this.total_rebounds_percentage = total_rebounds_percentage;
		this.def_turnovers = def_turnovers;

		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, this.year);
		c.set(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		this.year_date = c.getTime();

		NbaTeamLookup();
		NbaCityLookup();
	}

	public BasketballObject(String name, String team, Integer year, boolean postseason, double games_played,
			double minutes_played, double points_per_game, double field_goal_made, double field_goal_attempted,
			double field_goal_percentage, double two_points_made, double two_points_attempted,
			double two_points_percentage, double three_points_made, double three_points_attempted,
			double three_points_percentage, double free_throws_made, double free_throws_attempted,
			double free_throws_percentage, double offensive_rebounds, double defensive_rebounds, double total_rebounds,
			double assists, double steals, double blocks, double turnovers, double fouls) {

		this.name = name;
		this.team = team;
		this.year = year;
		this.postseason = postseason;
		this.games_played = games_played;
		this.minutes_played = minutes_played;
		this.points_per_game = points_per_game;
		this.field_goal_made = field_goal_made;
		this.field_goal_attempted = field_goal_attempted;
		this.field_goal_percentage = field_goal_percentage;
		this.two_points_made = two_points_made;
		this.two_points_attempted = two_points_attempted;
		this.two_points_percentage = two_points_percentage;
		this.three_points_made = three_points_made;
		this.three_points_attempted = three_points_attempted;
		this.three_points_percentage = three_points_percentage;
		this.free_throws_made = free_throws_made;
		this.free_throws_attempted = free_throws_attempted;
		this.free_throws_percentage = free_throws_percentage;
		this.offensive_rebounds = offensive_rebounds;
		this.defensive_rebounds = defensive_rebounds;
		this.total_rebounds = total_rebounds;
		this.assists = assists;
		this.steals = steals;
		this.blocks = blocks;
		this.turnovers = turnovers;
		this.fouls = fouls;

		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, this.year);
		c.set(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		this.year_date = c.getTime();

		NbaTeamLookup();
		NbaCityLookup();
	}
	
	private void NbaTeamLookup() {
//		String[] team_name = this.team.split(" ");
//		String last_word = team_name[team_name.length - 1];
		
		String team_name = this.team;
		
		try {
			switch (team_name) {
			case "Golden State":
				this.team = "Warriors";
				break;
			case "Golden State Warriors":
				this.team = "Warriors";
				break;
			case "Golden St.":
				this.team = "Warriors";
				break;
			case "Oklahoma City Thunder":
				this.team = "Thunder";
				break;
			case "Oklahoma City":
				this.team = "Thunder";
				break;
			case "Sacramento Kings":
				this.team = "Kings";
				break;
			case "Sacramento":
				this.team = "Kings";
				break;
			case "Houston Rockets":
				this.team = "Rockets";
				break;
			case "Houston":
				this.team = "Rockets";
				break;
			case "Boston Celtics":
				this.team = "Celtics";
				break;
			case "Boston":
				this.team = "Celtics";
				break;
			case "Portland Trail Blazers":
				this.team = "Trailblazers";
				break;
			case "Portland":
				this.team = "Trailblazers";
				break;
			case "Los Angeles Clippers":
				this.team = "Clippers";
				break;
			case "LA Clippers":
				this.team = "Clippers";
				break;
			case "L.A. Clippers":
				this.team = "Clippers";
				break;
			case "Cleveland Cavaliers":
				this.team = "Cavaliers";
				break;
			case "Cleveland":
				this.team = "Cavaliers";
				break;
			case "Washington Wizards":
				this.team = "Wizards";
				break;
			case "Washington":
				this.team = "Wizards";
				break;
			case "San Antonio Spurs":
				this.team = "Spurs";
				break;
			case "San Antonio":
				this.team = "Spurs";
				break;
			case "Charlotte Hornets":
				this.team = "Hornets";
				break;
			case "Charlotte":
				this.team = "Hornets";
				break;
			case "Atlanta Hawks":
				this.team = "Hawks";
				break;
			case "Atlanta":
				this.team = "Hawks";
				break;
			case "Toronto Raptors":
				this.team = "Raptors";
				break;
			case "Toronto":
				this.team = "Raptors";
				break;
			case "New Orleans Pelicans":
				this.team = "Pelicans";
				break;
			case "New Orleans":
				this.team = "Pelicans";
				break;
			case "Minnesota Timberwolves":
				this.team = "Timberwolves";
				break;
			case "Minnesota":
				this.team = "Timberwolves";
				break;
			case "Dallas Mavericks":
				this.team = "Mavericks";
				break;
			case "Dallas":
				this.team = "Mavericks";
				break;
			case "Indiana Pacers":
				this.team = "Pacers";
				break;
			case "Indiana":
				this.team = "Pacers";
				break;
			case "Orlando Magic":
				this.team = "Magic";
				break;
			case "Orlando":
				this.team = "Magic";
				break;
			case "Detroit Pistons":
				this.team = "Pistons";
				break;
			case "Detroit":
				this.team = "Pistons";
				break;
			case "Denver Nuggets":
				this.team = "Nuggets";
				break;
			case "Denver":
				this.team = "Nuggets";
				break;
			case "Chicago Bulls":
				this.team = "Bulls";
				break;
			case "Chicago":
				this.team = "Bulls";
				break;
			case "Phoenix Suns":
				this.team = "Suns";
				break;
			case "Phoenix":
				this.team = "Suns";
				break;
			case "Miami Heat":
				this.team = "Heat";
				break;
			case "Miami":
				this.team = "Heat";
				break;
			case "Memphis Grizzlies":
				this.team = "Grizzlies";
				break;
			case "Memphis":
				this.team = "Grizzlies";
				break;
			case "Milwaukee Bucks":
				this.team = "Bucks";
				break;
			case "Milwaukee":
				this.team = "Bucks";
				break;
			case "Brooklyn Nets":
				this.team = "Nets";
				break;
			case "Brooklyn":
				this.team = "Nets";
				break;
			case "New York Knicks":
				this.team = "Knicks";
				break;
			case "New York":
				this.team = "Knicks";
				break;
			case "Utah Jazz":
				this.team = "Jazz";
				break;
			case "Utah":
				this.team = "Jazz";
				break;
			case "Philadelphia 76ers":
				this.team = "76ers";
				break;
			case "Philadelphia":
				this.team = "76ers";
				break;
			case "Los Angeles Lakers":
				this.team = "Lakers";
				break;
			case "LA Lakers":
				this.team = "Lakers";
				break;
			case "L.A. Lakers":
				this.team = "Lakers";
				break;
			default:				
				System.out.println("The team name is: '" + this.team + "'");
				break;
			}
		} catch (Exception ex) {
			System.out.println("Error finding match for: " + this.year.toString() + " " + this.team);
		}
	}

	private void NbaCityLookup() {
		String[] team_name = this.team.split(" ");
		String last_word = team_name[team_name.length - 1];
		try {
			switch (last_word) {
			case "Warriors":
				this.city = "Oakland";
				break;

			case "Pacers":
				this.city = "Indianapolis";
				break;

			case "Timberwolves":
				this.city = "Minneapolis";
				break;

			case "Jazz":
				this.city = "Salt Lake City";
				break;

			case "Pelicans":
				this.city = "New Orleans";
				break;
				
			case "Lakers":
			case "Clippers":
				this.city = "Los Angeles";
				break;
				
			case "Knicks":
				this.city = "New York";
				break;
				
			case "Thunder":
				this.city = "Oklahoma City";
				break;
						
			case "Spurs":
				this.city = "San Antonio";
				break;
				
			case "Cavaliers":
				this.city = "Cleveland";
				break;
				
			case "Celtics":
				this.city = "Boston";
				break;
				
			case "Hawks":
				this.city = "Atlanta";
				break;
				
			case "Grizzlies":
				this.city = "Memphis";
				break;
				
			case "Trailblazers":	
			case "Blazers":
				this.city = "Portland";
				break;
				
			case "Raptors":
				this.city = "Toronto";
				break;
				
			case "Heat":
				this.city = "Miami";
				break;
				
			case "Bobcats":
			case "Hornets":
				this.city = "Charlotte";
				break;
				
			case "Bulls":
				this.city = "Chicago";
				break;

			case "Rockets":
				this.city = "Houston";
				break;
				
			case "Mavericks":
				this.city = "Dallas";
				break;
								
			case "Kings":
				this.city = "Sacramento";
				break;
				
			case "Pistons":
				this.city = "Detroit";
				break;
				
			case "76ers":
				this.city = "Philadelphia";
				break;
				
			case "Bullets":
			case "Wizards":
				this.city = "Washington";
				break;
				
			case "Suns":
				this.city = "Phoenix";
				break;
				
			case "Nets":
				this.city = "Brooklyn";
				break;
				
			case "Magic":
				this.city = "Orlando";
				break;
				
			case "Nuggets":
				this.city = "Denver";
				break;
				
			case "Sonics":
				this.city = "Seattle";
				break;	
				
			case "Bucks":
				this.city = "Milwaukee";
				break;
				
			default:				
				switch(this.team){
				case "Golden State":
					this.city = "Oakland";
					break;
					
				case "Utah":
					this.city = "Salt Lake City";
					break;
					
				case "Indiana":
					this.city = "Indianapolis";
					break;
					
					default:
						System.out.println("Need city mapping for: '" + this.team + "'");
						this.city = "";
				}
				break;
			}
		} catch (Exception ex) {
			System.out.println("Error finding match for: " + this.year.toString() + " " + this.team);
		}
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public boolean getPostseason() {
		return postseason;
	}

	public void setPostseason(boolean postseason) {
		this.postseason = postseason;
	}

	public double getGames_played() {
		return games_played;
	}

	public void setGames_played(double games_played) {
		this.games_played = games_played;
	}

	public double getPoints_per_game() {
		return points_per_game;
	}

	public void setPoints_per_game(double points_per_game) {
		this.points_per_game = points_per_game;
	}

	public double getField_goal_made() {
		return field_goal_made;
	}

	public void setField_goal_made(double field_goal_made) {
		this.field_goal_made = field_goal_made;
	}

	public double getField_goal_attempted() {
		return field_goal_attempted;
	}

	public void setField_goal_attempted(double field_goal_attempted) {
		this.field_goal_attempted = field_goal_attempted;
	}

	public double getField_goal_percentage() {
		return field_goal_percentage;
	}

	public void setField_goal_percentage(double field_goal_percentage) {
		this.field_goal_percentage = field_goal_percentage;
	}

	public void setTwo_points_attempted(double two_points_attempted) {
		this.two_points_attempted = two_points_attempted;
	}

	public double getTwo_points_attempted() {
		return two_points_attempted;
	}

	public void setTwo_points_made(double two_points_made) {
		this.two_points_made = two_points_made;
	}

	public double getTwo_points_made() {
		return two_points_made;
	}

	public double getTwo_points_percentage() {
		return two_points_percentage;
	}

	public void setTwo_points_percentage(double two_points_percentage) {
		this.two_points_percentage = two_points_percentage;
	}

	public double getThree_points_made() {
		return three_points_made;
	}

	public void setThree_points_made(double three_points_made) {
		this.three_points_made = three_points_made;
	}

	public double getThree_points_attempted() {
		return three_points_attempted;
	}

	public void setThree_points_attempted(double three_points_attempted) {
		this.three_points_attempted = three_points_attempted;
	}

	public double getThree_points_percentage() {
		return three_points_percentage;
	}

	public void setThree_points_percentage(double three_points_percentage) {
		this.three_points_percentage = three_points_percentage;
	}

	public double getFree_throws_made() {
		return free_throws_made;
	}

	public void setFree_throws_made(double free_throws_made) {
		this.free_throws_made = free_throws_made;
	}

	public double getFree_throws_attempted() {
		return free_throws_attempted;
	}

	public void setFree_throws_attempted(double free_throws_attempted) {
		this.free_throws_attempted = free_throws_attempted;
	}

	public double getFree_throws_percentage() {
		return free_throws_percentage;
	}

	public void setFree_throws_percentage(double free_throws_percentage) {
		this.free_throws_percentage = free_throws_percentage;
	}

	public void setOffensive_rebounds(double offensive_rebounds) {
		this.offensive_rebounds = offensive_rebounds;
	}

	public double getOffensive_rebounds() {
		return offensive_rebounds;
	}

	public void setDefensive_rebounds(double defensive_rebounds) {
		this.defensive_rebounds = defensive_rebounds;
	}

	public double getDefensive_rebounds() {
		return defensive_rebounds;
	}

	public void setTotal_rebounds(double total_rebounds) {
		this.total_rebounds = total_rebounds;
	}

	public double getTotal_rebounds() {
		return total_rebounds;
	}

	public void setAssists(double assists) {
		this.assists = assists;
	}

	public double getAssists() {
		return assists;
	}

	public void setSteals(double steals) {
		this.steals = steals;
	}

	public double getSteals() {
		return steals;
	}

	public void setBlocks(double blocks) {
		this.blocks = blocks;
	}

	public double getBlocks() {
		return blocks;
	}

	public void setTurnovers(double turnovers) {
		this.turnovers = turnovers;
	}

	public double getTurnovers() {
		return turnovers;
	}

	public void setFouls(double fouls) {
		this.fouls = fouls;
	}

	public double getFouls() {
		return fouls;
	}

	public void setDef_points_per_game(double def_points_per_game) {
		this.def_points_per_game = def_points_per_game;
	}

	public double getDef_points_per_game() {
		return def_points_per_game;
	}

	public void setPoint_difference(double point_difference) {
		this.point_difference = point_difference;
	}

	public double getPoint_difference() {
		return point_difference;
	}

	public void setDef_field_goal_percentage(double def_field_goal_percentage) {
		this.def_field_goal_percentage = def_field_goal_percentage;
	}

	public double getDef_field_goal_percentage() {
		return def_field_goal_percentage;
	}

	public void setDef_three_points_percentage(double def_three_points_percentage) {
		this.def_three_points_percentage = def_three_points_percentage;
	}

	public double getDef_three_points_percentage() {
		return def_three_points_percentage;
	}

	public double getOffensive_rebounds_percentage() {
		return offensive_rebounds_percentage;
	}

	public void setOffensive_rebounds_percentage(double offensive_rebounds_percentage) {
		this.offensive_rebounds_percentage = offensive_rebounds_percentage;
	}

	public double getDefensive_rebounds_percentage() {
		return defensive_rebounds_percentage;
	}

	public void setDefensive_rebounds_percentage(double defensive_rebounds_percentage) {
		this.defensive_rebounds_percentage = defensive_rebounds_percentage;
	}

	public double getTotal_rebounds_percentage() {
		return total_rebounds_percentage;
	}

	public void setTotal_rebounds_percentage(double total_rebounds_percentage) {
		this.total_rebounds_percentage = total_rebounds_percentage;
	}

	public void setDef_turnovers(double def_turnovers) {
		this.def_turnovers = def_turnovers;
	}

	public double getDef_turnovers() {
		return def_turnovers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMinutes_played() {
		return minutes_played;
	}

	public void setMinutes_played(double minutes_played) {
		this.minutes_played = minutes_played;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getYearDate() {
		return year_date;
	}

	public void setYearDate(Date year_date) {
		this.year_date = year_date;
	}

}

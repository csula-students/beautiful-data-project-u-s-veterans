package edu.csula.datascience.nba.elasticsearch;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Collection;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.google.common.collect.Lists;
import io.searchbox.action.BulkableAction;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;

/**
 * A quick example app to send data to elastic search on AWS
 */
public class NBAJest {

	public static void main(String[] args) throws URISyntaxException, IOException {
		
		String indexName = "datascience";
        
		String typeName = "nbastats";
		
		String awsAddress = "http://search-big-data-nba-ukyi4kqstyyxx3qcihtdysgg24.us-west-2.es.amazonaws.com/";
		
		JestClientFactory factory = new JestClientFactory();
		
		factory.setHttpClientConfig(new HttpClientConfig
	            .Builder(awsAddress)
	            .multiThreaded(true)
	            .build());
		
		JestClient client = factory.getObject();
		
		// as usual process to connect to data source, we will need to set up
        // node and client// to read CSV file from the resource folder
        File csv = new File(ClassLoader.getSystemResource("nbastats.csv").toURI());
		
        try {
            // after reading the csv file, we will use CSVParser to parse through
            // the csv files
            CSVParser parser = CSVParser.parse(csv,
                Charset.defaultCharset(),
                CSVFormat.EXCEL.withHeader()
            );
            
            Collection<Basketball> teamStats = Lists.newArrayList();

            int count = 0;

            // for each record, we will insert data into Elastic Search
            for (CSVRecord record: parser) {
                // cleaning up dirty data which doesn't have date or team
                if (
                    !record.get("year_date").isEmpty() &&
                    !record.get("team").isEmpty()
                ) {
                	
                	Basketball temp = new Basketball(
                    		record.get("year_date"),
                    		Integer.valueOf(record.get("year")),
                    		record.get("team"),
                    		record.get("city"),
                    		Boolean.valueOf(record.get("player")),
                    		record.get("player_name"),
                    		Boolean.valueOf(record.get("postseason")),
                    		Double.valueOf(record.get("games_played")),
                    		Double.valueOf(record.get("minutes_played")), 
                    		Double.valueOf(record.get("points_per_game")), 
                    		Double.valueOf(record.get("def_points_per_game")), 
                    		Double.valueOf(record.get("point_difference")), 
                    		Double.valueOf(record.get("field_goal_made")), 
                    		Double.valueOf(record.get("field_goal_attempted")),
                    		Double.valueOf(record.get("field_goal_percentage")), 
                    		Double.valueOf(record.get("def_field_goal_percentage")),
                    		Double.valueOf(record.get("two_points_made")), 
                    		Double.valueOf(record.get("two_points_attempted")),
                    		Double.valueOf(record.get("two_points_percentage")), 
                    		Double.valueOf(record.get("three_points_made")), 
                    		Double.valueOf(record.get("three_points_attempted")),
                    		Double.valueOf(record.get("three_points_percentage")), 
                    		Double.valueOf(record.get("def_three_points_percentage")), 
                    		Double.valueOf(record.get("free_throws_made")), 
                    		Double.valueOf(record.get("free_throws_attempted")),
                    		Double.valueOf(record.get("free_throws_percentage")), 
                    		Double.valueOf(record.get("offensive_rebounds")), 
                    		Double.valueOf(record.get("defensive_rebounds")), 
                    		Double.valueOf(record.get("total_rebounds")),
                    		Double.valueOf(record.get("offensive_rebounds_percentage")), 
                    		Double.valueOf(record.get("defensive_rebounds_percentage")),
                    		Double.valueOf(record.get("total_rebounds_percentage")), 
                    		Double.valueOf(record.get("assists")), 
                    		Double.valueOf(record.get("steals")), 
                    		Double.valueOf(record.get("blocks")), 
                    		Double.valueOf(record.get("turnovers")),
                    		Double.valueOf(record.get("def_turnovers")),
                    		Double.valueOf(record.get("fouls")),
                    		record.get("website")
                    );

                    if (count < 500) {
                    	
                    	teamStats.add(temp);         
                    	count ++;
                    	
                    } else {
                    	
                        try {
                            
                        	@SuppressWarnings("rawtypes")
							Collection<BulkableAction> actions = Lists.newArrayList();
                            
                            teamStats.stream()
                                .forEach(tmp -> {
                                    actions.add(new Index.Builder(tmp).build());
                                });
                            
                            Bulk.Builder bulk = new Bulk.Builder()
                                .defaultIndex(indexName)
                                .defaultType(typeName)
                                .addAction(actions);
                            
                            client.execute(bulk.build());
                            
                            count = 0;
                            
                            teamStats = Lists.newArrayList();
                            
                            System.out.println("Inserted 500 documents to cloud");
                        
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressWarnings("rawtypes")
			Collection<BulkableAction> actions = Lists.newArrayList();
            
            teamStats.stream()
                .forEach(tmp -> {
                    actions.add(new Index.Builder(tmp).build());
                });
            
            Bulk.Builder bulk = new Bulk.Builder()
                .defaultIndex(indexName)
                .defaultType(typeName)
                .addAction(actions);
            client.execute(bulk.build());
        
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("We are done! Yay!");
    }
        
	static class Basketball {
		
		final String year_date;
		final Integer year; 
		final String team;
		final String city;
		final boolean player;
		final String player_name; 
		final boolean postseason; 
		final double games_played;
		final double minutes_played; 
		final double points_per_game; 
		final double def_points_per_game; 
		final double point_difference; 
		final double field_goal_made; 
		final double field_goal_attempted;
		final double field_goal_percentage; 
		final double def_field_goal_percentage;
		final double two_points_made; 
		final double two_points_attempted;
		final double two_points_percentage; 
		final double three_points_made; 
		final double three_points_attempted;
		final double three_points_percentage; 
		final double def_three_points_percentage; 
		final double free_throws_made; 
		final double free_throws_attempted;
		final double free_throws_percentage; 
		final double offensive_rebounds; 
		final double defensive_rebounds; 
		final double total_rebounds;
		final double offensive_rebounds_percentage; 
		final double defensive_rebounds_percentage;
		final double total_rebounds_percentage; 
		final double assists; 
		final double steals; 
		final double blocks; 
		final double turnovers;
		final double def_turnovers;
		final double fouls;
		final String website;
		
		public Basketball(
				
				String year_date,
				Integer year, 
				String team,
				String city,
				boolean player,
				String player_name, 
				boolean postseason, 
				double games_played,
				double minutes_played, 
				double points_per_game, 
				double def_points_per_game, 
				double point_difference, 
				double field_goal_made, 
				double field_goal_attempted,
				double field_goal_percentage, 
				double def_field_goal_percentage,
				double two_points_made, 
				double two_points_attempted,
				double two_points_percentage, 
				double three_points_made, 
				double three_points_attempted,
				double three_points_percentage, 
				double def_three_points_percentage, 
				double free_throws_made, 
				double free_throws_attempted,
				double free_throws_percentage, 
				double offensive_rebounds, 
				double defensive_rebounds, 
				double total_rebounds,
				double offensive_rebounds_percentage, 
				double defensive_rebounds_percentage,
				double total_rebounds_percentage, 
				double assists, 
				double steals, 
				double blocks, 
				double turnovers,
				double def_turnovers,
				double fouls,
				String website
				) {

			this.year_date = year_date;
			this.year = year;
			this.team = team;
			this.city = city;
			this.player = player;
			this.player_name = player_name;
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
			this.offensive_rebounds_percentage = offensive_rebounds_percentage;
			this.defensive_rebounds_percentage = defensive_rebounds_percentage;
			this.total_rebounds_percentage = total_rebounds_percentage;
			this.assists = assists;
			this.steals = steals;
			this.blocks = blocks;
			this.turnovers = turnovers;
			this.fouls = fouls;
			this.def_points_per_game = def_points_per_game;
			this.point_difference = point_difference;
			this.def_field_goal_percentage = def_field_goal_percentage;
			this.def_three_points_percentage = def_three_points_percentage;
			this.def_turnovers = def_turnovers;
			this.website = website;
		}

		public String getYear_date() {
			return year_date;
		}

		public Integer getYear() {
			return year;
		}

		public String getTeam() {
			return team;
		}

		public String getCity() {
			return city;
		}

		public String getPlayer_name() {
			return player_name;
		}

		public boolean isPostseason() {
			return postseason;
		}

		public double getGames_played() {
			return games_played;
		}

		public double getMinutes_played() {
			return minutes_played;
		}

		public double getPoints_per_game() {
			return points_per_game;
		}

		public double getDef_points_per_game() {
			return def_points_per_game;
		}

		public double getPoint_difference() {
			return point_difference;
		}

		public double getField_goal_made() {
			return field_goal_made;
		}

		public double getField_goal_attempted() {
			return field_goal_attempted;
		}

		public double getField_goal_percentage() {
			return field_goal_percentage;
		}

		public double getDef_field_goal_percentage() {
			return def_field_goal_percentage;
		}

		public double getTwo_points_made() {
			return two_points_made;
		}

		public double getTwo_points_attempted() {
			return two_points_attempted;
		}

		public double getTwo_points_percentage() {
			return two_points_percentage;
		}

		public double getThree_points_made() {
			return three_points_made;
		}

		public double getThree_points_attempted() {
			return three_points_attempted;
		}

		public double getThree_points_percentage() {
			return three_points_percentage;
		}

		public double getDef_three_points_percentage() {
			return def_three_points_percentage;
		}

		public double getFree_throws_made() {
			return free_throws_made;
		}

		public double getFree_throws_attempted() {
			return free_throws_attempted;
		}

		public double getFree_throws_percentage() {
			return free_throws_percentage;
		}

		public double getOffensive_rebounds() {
			return offensive_rebounds;
		}

		public double getDefensive_rebounds() {
			return defensive_rebounds;
		}

		public double getTotal_rebounds() {
			return total_rebounds;
		}

		public double getOffensive_rebounds_percentage() {
			return offensive_rebounds_percentage;
		}

		public double getDefensive_rebounds_percentage() {
			return defensive_rebounds_percentage;
		}

		public double getTotal_rebounds_percentage() {
			return total_rebounds_percentage;
		}

		public double getAssists() {
			return assists;
		}

		public double getSteals() {
			return steals;
		}

		public double getBlocks() {
			return blocks;
		}

		public double getTurnovers() {
			return turnovers;
		}

		public double getDef_turnovers() {
			return def_turnovers;
		}

		public double getFouls() {
			return fouls;
		}

		public boolean isPlayer() {
			return player;
		}

		public String getWebsite() {
			return website;
		}	
	}
}

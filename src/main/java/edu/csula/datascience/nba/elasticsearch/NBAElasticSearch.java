package edu.csula.datascience.nba.elasticsearch;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * A quick elastic search example app
 *
 * It will parse the csv file from the resource folder under main and send these
 * data to elastic search instance running locally
 *
 * After that we will be using elastic search to do full text search
 *
 * gradle command to run this app 'gradle esNbaStats'
 */
public class NBAElasticSearch {

	private final static String indexName = "datascience";
	private final static String typeName = "nbastats";

	public static void main(String[] args) throws URISyntaxException, IOException {

		Node node = nodeBuilder()
				.settings(Settings.builder().put("cluster.name", "ares").put("path.home", "elasticsearch-data")).node();

		Client client = node.client();

		// as usual process to connect to data source, we will need to set up
		// node and client
		// to read CSV file from the resource folder
		File csv = new File(ClassLoader.getSystemResource("nbastats.csv").toURI());

		// create bulk processor
		BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {

			@Override
			public void beforeBulk(long executionId, BulkRequest request) {
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
				System.out.println("Facing error while importing data to elastic search");
				failure.printStackTrace();
			}
		}).setBulkActions(10000).setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
				.setFlushInterval(TimeValue.timeValueSeconds(5)).setConcurrentRequests(1)
				.setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3)).build();

		// Gson library for sending json to elastic search
		Gson gson = new Gson();
		
        try {
            // after reading the csv file, we will use CSVParser to parse through
            // the csv files
            CSVParser parser = CSVParser.parse(
                csv,
                Charset.defaultCharset(),
                CSVFormat.EXCEL.withHeader()
            );

            // for each record, we will insert data into Elastic Search
            parser.forEach(record -> {
                // cleaning up dirty data which doesn't have time or temperature
                if (
                    !record.get("year").isEmpty() 
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

                    bulkProcessor.add(new IndexRequest(indexName, typeName)
                        .source(gson.toJson(temp))
                    );
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        /**
         * AGGREGATION
         */
        SearchResponse sr = node.client().prepareSearch(indexName)
            .setTypes(typeName)
            .setQuery(QueryBuilders.matchAllQuery())
            .addAggregation(AggregationBuilders.terms("stateAgg").field("state")
                    .size(Integer.MAX_VALUE)
            )
            .execute().actionGet();
        
        // Get your facet results
        Terms agg1 = sr.getAggregations().get("stateAgg");
        
        for (Terms.Bucket bucket: agg1.getBuckets()) {
            
        	System.out.println(bucket.getKey() + ": " + bucket.getDocCount());
        }
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

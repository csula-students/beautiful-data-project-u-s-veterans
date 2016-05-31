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

import edu.csula.datascience.nba.BasketballObject;

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
                    BasketballObject temp = new BasketballObject(
                    		Integer.valueOf(record.get("year")),
                    		record.get("team"),
                    		record.get("name"),
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
                    		Double.valueOf(record.get("fouls")) 
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

}

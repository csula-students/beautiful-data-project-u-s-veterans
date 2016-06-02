package edu.csula.datascience.nba;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class NBAPageCollector implements Collector<BasketballObject, BasketballObject> {
	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<Document> collection;

	public NBAPageCollector() {
		mongoClient = new MongoClient();

		database = mongoClient.getDatabase("datascience");

		collection = database.getCollection("nbastats");
		collection.drop();
	}

	@Override
	public Collection<BasketballObject> mungee(Collection<BasketballObject> src) {
		
		Collection<BasketballObject> clean = new ArrayList<BasketballObject>();

		for (BasketballObject stat : src) {
			// Determine cleaning process
			try {
				if (!stat.getTeam().contentEquals("") && stat.getPoints_per_game() > 0.0) {
					boolean duplicate = false;
					
					for (BasketballObject cStat : clean) {
						if (stat.getTeam().contentEquals(cStat.getTeam()) && stat.getPlayer_name().contentEquals("")) {
							duplicate = true;
							break;
						}
					}
					
					if (!duplicate) {
						
						if (stat.getPlayer_name() != null) {
							
							System.out.println(stat.getPlayer_name() + " : " + stat.getTeam() + 
									" : " + stat.getPoints_per_game());								
						} else {
							
							System.out.println(stat.getTeam() + " : " + 
									stat.getPoints_per_game());
						}
												
						clean.add(stat);
					}
				}
			} catch (Exception ex) {
			}
		}

		return clean;
	}

	@Override
	public void save(Collection<BasketballObject> data) {
		List<Document> documents = data.stream()
				.map(item -> new Document().append(NBAStatMapping.TEAM, item.getTeam())
						.append("city", item.getCity())
						.append("year", item.getYear())
						.append("year_date", item.getYearDate())
						.append("postseason", item.getPostseason())
						.append(NBAStatMapping.PLAYER_NAME, item.getPlayer_name())
						.append(NBAStatMapping.MIN_PLAYED, item.getMinutes_played())
						.append(NBAStatMapping.GAMES_PLAYED, item.getGames_played())
						.append(NBAStatMapping.PTS_PER_GAME, item.getPoints_per_game())
						.append(NBAStatMapping.DEF_PTS_PER_GAME, item.getDef_points_per_game())
						.append(NBAStatMapping.PT_DIFF, item.getPoint_difference())
						.append(NBAStatMapping.FG_MADE, item.getField_goal_made())
						.append(NBAStatMapping.FG_ATTEMPTED, item.getField_goal_attempted())
						.append(NBAStatMapping.FG_PERCENTAGE, item.getField_goal_percentage())
						.append(NBAStatMapping.DEF_FG_PERCENTAGE, item.getDef_field_goal_percentage())
						.append(NBAStatMapping.TWO_PT_MADE, item.getTwo_points_made())
						.append(NBAStatMapping.TWO_PT_ATTEMPTED, item.getTwo_points_attempted())
						.append(NBAStatMapping.TWO_PT_PERCENTAGE, item.getTwo_points_percentage())
						.append(NBAStatMapping.THREE_PT_MADE, item.getThree_points_made())
						.append(NBAStatMapping.THREE_PT_ATTEMPTED, item.getThree_points_attempted())
						.append(NBAStatMapping.THREE_PT_PERCENTAGE, item.getThree_points_percentage())
						.append(NBAStatMapping.DEF_THREE_PT_PERCENTAGE, item.getDef_three_points_percentage())
						.append(NBAStatMapping.FT_MADE, item.getFree_throws_made())
						.append(NBAStatMapping.FT_ATTEMPTED, item.getFree_throws_attempted())
						.append(NBAStatMapping.FT_PERCENTAGE, item.getFree_throws_percentage())
						.append(NBAStatMapping.OFF_REBOUNDS, item.getOffensive_rebounds())
						.append(NBAStatMapping.DEF_REBOUNDS, item.getDefensive_rebounds())
						.append(NBAStatMapping.TOTAL_REBOUNDS, item.getTotal_rebounds())
						.append(NBAStatMapping.OFF_REBOUNDS_PERCENTAGE, item.getOffensive_rebounds_percentage())
						.append(NBAStatMapping.DEF_REBOUNDS_PERCENTAGE, item.getDefensive_rebounds_percentage())
						.append(NBAStatMapping.TOTAL_REBOUNDS_PERCENTAGE, item.getTotal_rebounds_percentage())
						.append(NBAStatMapping.ASSISTS, item.getAssists())
						.append(NBAStatMapping.STEALS, item.getSteals())
						.append(NBAStatMapping.BLOCKS, item.getBlocks())
						.append(NBAStatMapping.TURNOVERS, item.getTurnovers())
						.append(NBAStatMapping.DEF_TURNOVERS, item.getDef_turnovers())
						.append(NBAStatMapping.FOULS, item.getFouls()))
				.collect(Collectors.toList());
		collection.insertMany(documents);
	}
}

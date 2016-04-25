package edu.csula.datascience.jsouptest;

import edu.csula.datascience.jsouptest.NBAStatMapping;

import java.io.IOException;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NBAPageSource implements Source<BasketballObject> {

	private String url;
	private Integer year;
	private NBAStatMapping mapping;
	private Document doc;
	private Elements rows;
	private boolean rows_returned;

	// Mapping should not be modified once passed to source object
	NBAPageSource(String url, Integer year, NBAStatMapping mapping) {
		this.url = url;
		this.year = year;
		this.mapping = mapping;

		// Sets flag for hasNext()
		// If connection to source URL is successful, designate hasNext() to
		// true
		this.rows_returned = true;
		try {
			doc = Jsoup.connect(url).get();
			rows_returned = false;
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public String getUrl()
	{
		return url;
	}

	public Integer getYear()
	{
		return year;
	}
	
	@Override
	public boolean hasNext() {
		return !rows_returned;
	}

	@Override
	public Collection<BasketballObject> next() {
		Collection<BasketballObject> stats = new ArrayList<BasketballObject>();
		Elements rows = doc.getElementsByTag("tr").not("tr.colhead, tr.footer").not("thead > tr");

		for (Element row : rows) {
			Elements tds = row.getElementsByTag("td");

			String team = "";
			Double games_played = 0.0;
			Double points_per_game = 0.0;
			Double field_goal_made = 0.0;
			Double field_goal_attempted = 0.0;
			Double field_goal_percentage = 0.0;
			Double two_pt_made = 0.0;
			Double two_pt_attempted = 0.0;
			Double two_pt_percentage = 0.0;
			Double three_points_made = 0.0;
			Double three_points_attempted = 0.0;
			Double three_points_percentage = 0.0;
			Double free_throws_made = 0.0;
			Double free_throws_attempted = 0.0;
			Double free_throws_percentage = 0.0;
			Double off_rebounds = 0.0;
			Double def_rebounds = 0.0;
			Double total_rebounds = 0.0;
			Double assists = 0.0;
			Double steals = 0.0;
			Double blocks = 0.0;
			Double turnovers = 0.0;
			Double fouls = 0.0;
			Double def_points_per_game = 0.0;
			Double def_field_goal_percentage = 0.0;
			Double point_difference = 0.0;
			Double def_three_points_percentage = 0.0;
			Double def_turnovers = 0.0;

			for (Entry<Integer, String> stat : mapping.entrySet()) {
				try {
					switch (stat.getValue()) {
					case NBAStatMapping.TEAM:
						team = tds.get(stat.getKey()).text();
						//System.out.println("team: " + team);
						break;
					case NBAStatMapping.GAMES_PLAYED:
						try{
							games_played = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("games_played: " + games_played);
						}catch(Exception ex)	{}
						break;
					case NBAStatMapping.PTS_PER_GAME:
						try{
							points_per_game = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("points_per_game: " + points_per_game);
						} catch(Exception ex){}
						break;
					case NBAStatMapping.FG_MADE:
						try{
							field_goal_made = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("field_goal_made: " + field_goal_made);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.FG_ATTEMPTED:
						try{
							field_goal_attempted = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("field_goal_attempted: " + field_goal_attempted);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.FG_PERCENTAGE:
						try{
							field_goal_percentage = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("field_goal_percentage: " + field_goal_percentage);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.TWO_PT_MADE:
						try{
							two_pt_made = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("two_pt_made: " + two_pt_made);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.TWO_PT_ATTEMPTED:
						try{
							two_pt_attempted = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("two_pt_attempted: " + two_pt_attempted);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.TWO_PT_PERCENTAGE:
						try{
							two_pt_percentage = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("two_pt_percentage: " + two_pt_percentage);
						}catch(Exception ex){}
					case NBAStatMapping.THREE_PT_MADE:
						try{
							three_points_made = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("three_points_made: " + three_points_made);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.THREE_PT_ATTEMPTED:
						try{
							three_points_attempted = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("three_points_attempted: " + three_points_attempted);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.THREE_PT_PERCENTAGE:
						try{
							three_points_percentage = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("three_points_percentage: " + three_points_percentage);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.FT_MADE:
						try{
							free_throws_made = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("free_throws_made: " + free_throws_made);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.FT_ATTEMPTED:
						try{
							free_throws_attempted = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("free_throws_attempted: " + free_throws_attempted);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.FT_PERCENTAGE:
						try{
							free_throws_percentage = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("free_throws_percentage: " + free_throws_percentage);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.OFF_REBOUNDS:
						try{
							off_rebounds = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("off_rebounds: " + off_rebounds);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.DEF_REBOUNDS:
						try{
							def_rebounds = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("def_rebounds: " + def_rebounds);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.TOTAL_REBOUNDS:
						try{
							total_rebounds = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("total_rebounds: " + total_rebounds);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.ASSISTS:
						try{
							assists = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("assists: " + assists);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.STEALS:
						try{
							steals = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("steals: " + steals);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.BLOCKS:
						try{
							blocks = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("blocks: " + blocks);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.TURNOVERS:
						try{
							turnovers = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("turnovers: " + turnovers);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.FOULS:
						try{
							fouls = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("fouls: " + fouls);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.DEF_PTS_PER_GAME:
						try{
							def_points_per_game = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("def_points_per_game: " + def_points_per_game);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.DEF_FG_PERCENTAGE:
						try{
							def_field_goal_percentage = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("def_field_goal_percentage: " + def_field_goal_percentage);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.PT_DIFF:
						try{
							point_difference = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("point_difference: " + point_difference);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.DEF_THREE_PT_PERCENTAGE:
						try{
							def_three_points_percentage = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("def_three_points_percentage: " + def_three_points_percentage);
						}catch(Exception ex){}
						break;
					case NBAStatMapping.DEF_TURNOVERS:
						try{
							def_turnovers = Double.parseDouble(tds.get(stat.getKey()).text());
							//System.out.println("def_turnovers: " + def_turnovers);
						}catch(Exception ex){}
						break;
					default:
						// set field to 0.0
						break;
					}
				} catch (Exception ex) {
					System.out.println(this.url);
					for(org.jsoup.nodes.Element td : tds){
						System.out.println(td.text());
					}
					 //ex.printStackTrace();
				}
			}

			try {
				BasketballObject newObj = new BasketballObject(team, year, games_played,
						points_per_game, field_goal_made,
						field_goal_attempted, field_goal_percentage,
						two_pt_made, two_pt_attempted, two_pt_percentage,
						three_points_made, three_points_attempted,
						three_points_percentage, free_throws_made,
						free_throws_attempted, free_throws_percentage,
						off_rebounds, def_rebounds,
						total_rebounds, assists, steals,
						blocks, turnovers, fouls,
						def_points_per_game, def_field_goal_percentage,
						point_difference, def_three_points_percentage,
						def_turnovers);
				stats.add(newObj);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		rows_returned = true;
		return stats;
	}
}
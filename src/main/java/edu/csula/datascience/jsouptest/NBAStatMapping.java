package edu.csula.datascience.jsouptest;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;

// This class handles the variety of stats within each source.
// Based on the logic in the NBAPageCollector, the keys will correspond to the
// case values and set the appropriate fields on the BasketballObject
public class NBAStatMapping {

	private Map<Integer, String> mapping;

	public static final String TEAM = "team";
	public static final String GAMES_PLAYED = "games_played";
	public static final String PTS_PER_GAME = "points_per_game";
	public static final String FG_MADE = "field_goal_made";
	public static final String FG_ATTEMPTED = "field_goal_attempted";
	public static final String FG_PERCENTAGE = "field_goal_percentage";
	public static final String TWO_PT_MADE = "two_points_made";
	public static final String TWO_PT_ATTEMPTED = "two_points_attempted";
	public static final String TWO_PT_PERCENTAGE = "two_points_percentage";
	public static final String THREE_PT_MADE = "three_points_made";
	public static final String THREE_PT_ATTEMPTED = "three_points_attempted";
	public static final String THREE_PT_PERCENTAGE = "three_points_percentage";
	public static final String FT_MADE = "free_throws_made";
	public static final String FT_ATTEMPTED = "free_throws_attempted";
	public static final String FT_PERCENTAGE = "free_throws_percentage";
	public static final String OFF_REBOUNDS = "offensive_rebounds";
	public static final String DEF_REBOUNDS = "defensive_rebounds";
	public static final String TOTAL_REBOUNDS = "total_rebounds";
	public static final String ASSISTS = "assists";
	public static final String STEALS = "steals";
	public static final String BLOCKS = "blocks";
	public static final String TURNOVERS = "turnovers";
	public static final String FOULS = "fouls";
	public static final String DEF_PTS_PER_GAME = "def_points_per_game";
	public static final String DEF_FG_PERCENTAGE = "def_field_goal_percentage";
	public static final String PT_DIFF = "point_difference";
	public static final String DEF_THREE_PT_PERCENTAGE = "def_three_points_percentage";
	public static final String DEF_TURNOVERS = "def_turnovers";

	public NBAStatMapping() {
		mapping = new HashMap();
	}

	public NBAStatMapping(Map<Integer, String> mapping) {
		this.mapping = mapping;
	}

	public boolean addMapping(Integer key, String value) {
		if (mapping.containsKey(key) || mapping.containsValue(value))
			return false;

		mapping.put(key, value);
		return true;
	}

	public boolean removeMapping(Integer key) {
		if (mapping.containsKey(key)) {
			mapping.remove(key);
			return true;
		}

		return false;
	}

	public Set<Map.Entry<Integer, String>> entrySet(){
		return mapping.entrySet();
	}
}
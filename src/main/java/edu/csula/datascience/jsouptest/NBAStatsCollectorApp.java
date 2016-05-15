package edu.csula.datascience.jsouptest;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class NBAStatsCollectorApp {

	public static void main(String[] args) {

		List<NBAPageSource> sources = initSources();
		List<BasketballObject> regularSeasonStats = new ArrayList<BasketballObject>();
		NBAPageCollector collector = new NBAPageCollector();

		for (NBAPageSource source : sources) {
			try {
				Collection<BasketballObject> batch;
				while (source.hasNext()) {
					Collection<BasketballObject> sourceCollection = source.next();
					batch = collector.mungee(sourceCollection);
					regularSeasonStats.addAll(batch);
					System.out.println("Source (" + sourceCollection.size() + "): Batch(" + batch.size() + "); " + source.getUrl());
					// save here once mongo is set up
					collector.save(batch);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static List<NBAPageSource> initSources() {
		System.out.println("Initializing sources...");
		List<NBAPageSource> list = new ArrayList<NBAPageSource>();

		// CBS Sports
		String cbs_url = "http://www.cbssports.com/nba/stats/teamsort/NBA/year-2015-season-regularseason-category-scoringpergame-type-team";
		Integer cbs_year = 2016;
		NBAStatMapping cbs_mapping = new NBAStatMapping();
		cbs_mapping.addMapping(0, NBAStatMapping.TEAM);
		cbs_mapping.addMapping(1, NBAStatMapping.GAMES_PLAYED);
		cbs_mapping.addMapping(2, NBAStatMapping.PTS_PER_GAME);
		cbs_mapping.addMapping(3, NBAStatMapping.FG_MADE);
		cbs_mapping.addMapping(4, NBAStatMapping.FG_ATTEMPTED);
		cbs_mapping.addMapping(5, NBAStatMapping.FG_PERCENTAGE);
		cbs_mapping.addMapping(6, NBAStatMapping.THREE_PT_MADE);
		cbs_mapping.addMapping(7, NBAStatMapping.THREE_PT_ATTEMPTED);
		cbs_mapping.addMapping(8, NBAStatMapping.THREE_PT_PERCENTAGE);
		cbs_mapping.addMapping(9, NBAStatMapping.FT_MADE);
		cbs_mapping.addMapping(10, NBAStatMapping.FT_ATTEMPTED);
		cbs_mapping.addMapping(11, NBAStatMapping.FT_PERCENTAGE);
		NBAPageSource cbssports_source = new NBAPageSource(cbs_url, cbs_year, false, cbs_mapping, "tr");

		list.add(cbssports_source);
				
		// Draft Express (1979-2016)
		Integer startYear = 1979;
		Integer endYear = 2016;
		String draftExpress_url = "http://www.draftexpress.com/teamstats.php?league=NBA&year=2016&per=game";
		NBAStatMapping draftExpress_mapping = new NBAStatMapping();
		draftExpress_mapping.addMapping(0, NBAStatMapping.TEAM);
		draftExpress_mapping.addMapping(1, NBAStatMapping.GAMES_PLAYED);
		draftExpress_mapping.addMapping(2, NBAStatMapping.PTS_PER_GAME);
		draftExpress_mapping.addMapping(3, NBAStatMapping.FG_MADE);
		draftExpress_mapping.addMapping(4, NBAStatMapping.FG_ATTEMPTED);
		draftExpress_mapping.addMapping(5, NBAStatMapping.FG_PERCENTAGE);
		draftExpress_mapping.addMapping(6, NBAStatMapping.TWO_PT_MADE);
		draftExpress_mapping.addMapping(7, NBAStatMapping.TWO_PT_ATTEMPTED);
		draftExpress_mapping.addMapping(8, NBAStatMapping.TWO_PT_PERCENTAGE);
		draftExpress_mapping.addMapping(9, NBAStatMapping.THREE_PT_MADE);
		draftExpress_mapping.addMapping(10, NBAStatMapping.THREE_PT_ATTEMPTED);
		draftExpress_mapping.addMapping(11, NBAStatMapping.THREE_PT_PERCENTAGE);
		draftExpress_mapping.addMapping(12, NBAStatMapping.FT_MADE);
		draftExpress_mapping.addMapping(13, NBAStatMapping.FT_ATTEMPTED);
		draftExpress_mapping.addMapping(14, NBAStatMapping.FT_PERCENTAGE);
		draftExpress_mapping.addMapping(15, NBAStatMapping.OFF_REBOUNDS);
		draftExpress_mapping.addMapping(16, NBAStatMapping.DEF_REBOUNDS);
		draftExpress_mapping.addMapping(17, NBAStatMapping.TOTAL_REBOUNDS);
		draftExpress_mapping.addMapping(18, NBAStatMapping.ASSISTS);
		draftExpress_mapping.addMapping(19, NBAStatMapping.STEALS);
		draftExpress_mapping.addMapping(20, NBAStatMapping.BLOCKS);
		draftExpress_mapping.addMapping(21, NBAStatMapping.TURNOVERS);
		draftExpress_mapping.addMapping(22, NBAStatMapping.FOULS);

		for (int i = endYear; i >= startYear; i--) {
			String year_url = draftExpress_url.replaceAll("year=\\d\\d\\d\\d", "year=" + i);
			// System.out.println(year_url);
			NBAPageSource de_source = new NBAPageSource(year_url, i, false, draftExpress_mapping, "tr");
			list.add(de_source);
		}

		// ESPN (1999 - 2016)
		startYear = 1999;
		endYear = 2016;
		String espn_url = "http://espn.go.com/nba/statistics/team/_/stat/team-comparison-per-game/sort/avgPoints/year/2016/seasontype/2";
		NBAStatMapping espn_mapping = new NBAStatMapping();
		espn_mapping.addMapping(1, NBAStatMapping.TEAM);
		espn_mapping.addMapping(2, NBAStatMapping.PTS_PER_GAME);
		espn_mapping.addMapping(3, NBAStatMapping.DEF_PTS_PER_GAME);
		espn_mapping.addMapping(4, NBAStatMapping.PT_DIFF);
		espn_mapping.addMapping(5, NBAStatMapping.FG_PERCENTAGE);
		espn_mapping.addMapping(6, NBAStatMapping.DEF_FG_PERCENTAGE);
		espn_mapping.addMapping(7, NBAStatMapping.THREE_PT_PERCENTAGE);
		espn_mapping.addMapping(8, NBAStatMapping.DEF_THREE_PT_PERCENTAGE);
		espn_mapping.addMapping(9, NBAStatMapping.FT_PERCENTAGE);
		espn_mapping.addMapping(10, NBAStatMapping.OFF_REBOUNDS_PERCENTAGE);
		espn_mapping.addMapping(11, NBAStatMapping.DEF_REBOUNDS_PERCENTAGE);
		espn_mapping.addMapping(12, NBAStatMapping.TOTAL_REBOUNDS_PERCENTAGE);
		espn_mapping.addMapping(13, NBAStatMapping.TURNOVERS);
		espn_mapping.addMapping(14, NBAStatMapping.DEF_TURNOVERS);

		for (int i = endYear; i >= startYear; i--) {
			String year_url = espn_url.replaceAll("year/\\d\\d\\d\\d", "year/" + i);
			//System.out.println(year_url);
			NBAPageSource espn_source = new NBAPageSource(year_url, i, false, espn_mapping, "tr");
			list.add(espn_source);
		}
		
		// CBS Sports Postseason
		String cbs_url_post = "http://www.cbssports.com/nba/stats/teamsort/NBA/year-2015-season-postseason-category-scoringpergame-type-team";
		NBAStatMapping cbs_mapping_post = new NBAStatMapping();
		cbs_mapping_post.addMapping(0, NBAStatMapping.TEAM);
		cbs_mapping_post.addMapping(1, NBAStatMapping.GAMES_PLAYED);
		cbs_mapping_post.addMapping(2, NBAStatMapping.PTS_PER_GAME);
		cbs_mapping_post.addMapping(3, NBAStatMapping.FG_MADE);
		cbs_mapping_post.addMapping(4, NBAStatMapping.FG_ATTEMPTED);
		cbs_mapping_post.addMapping(5, NBAStatMapping.FG_PERCENTAGE);
		cbs_mapping_post.addMapping(6, NBAStatMapping.THREE_PT_MADE);
		cbs_mapping_post.addMapping(7, NBAStatMapping.THREE_PT_ATTEMPTED);
		cbs_mapping_post.addMapping(8, NBAStatMapping.THREE_PT_PERCENTAGE);
		cbs_mapping_post.addMapping(9, NBAStatMapping.FT_MADE);
		cbs_mapping_post.addMapping(10, NBAStatMapping.FT_ATTEMPTED);
		cbs_mapping_post.addMapping(11, NBAStatMapping.FT_PERCENTAGE);
		NBAPageSource cbssports_source_post = new NBAPageSource(cbs_url_post, cbs_year, true, cbs_mapping_post, "tr");

		list.add(cbssports_source_post);
		
		// ESPN (1999 - 2016) Postseason
		startYear = 2000;
		endYear = 2016;
		String espn_url_post = "http://espn.go.com/nba/statistics/team/_/stat/team-comparison-per-game/sort/avgPoints/year/2016";
		NBAStatMapping espn_mapping_post = new NBAStatMapping();
		espn_mapping_post.addMapping(1, NBAStatMapping.TEAM);
		espn_mapping_post.addMapping(2, NBAStatMapping.PTS_PER_GAME);
		espn_mapping_post.addMapping(3, NBAStatMapping.DEF_PTS_PER_GAME);
		espn_mapping_post.addMapping(4, NBAStatMapping.PT_DIFF);
		espn_mapping_post.addMapping(5, NBAStatMapping.FG_PERCENTAGE);
		espn_mapping_post.addMapping(6, NBAStatMapping.DEF_FG_PERCENTAGE);
		espn_mapping_post.addMapping(7, NBAStatMapping.THREE_PT_PERCENTAGE);
		espn_mapping_post.addMapping(8, NBAStatMapping.DEF_THREE_PT_PERCENTAGE);
		espn_mapping_post.addMapping(9, NBAStatMapping.FT_PERCENTAGE);
		espn_mapping_post.addMapping(10, NBAStatMapping.OFF_REBOUNDS_PERCENTAGE);
		espn_mapping_post.addMapping(11, NBAStatMapping.DEF_REBOUNDS_PERCENTAGE);
		espn_mapping_post.addMapping(12, NBAStatMapping.TOTAL_REBOUNDS_PERCENTAGE);
		espn_mapping_post.addMapping(13, NBAStatMapping.TURNOVERS);
		espn_mapping_post.addMapping(14, NBAStatMapping.DEF_TURNOVERS);

		for (int i = endYear; i >= startYear; i--) {
			String year_url = espn_url_post.replaceAll("year/\\d\\d\\d\\d", "year/" + i);
			//System.out.println(year_url);
			NBAPageSource espn_source_post = new NBAPageSource(year_url, i, true, espn_mapping_post, "tr");
			list.add(espn_source_post);
		}
		
		//Yahoo Sports
		startYear = 2003;
		endYear = 2015;
		String yahoo_url = "http://sports.yahoo.com/nba/stats/byteam?cat1=Total&cat2=team&sort=232&conference=NBA&year=season_2015";
		NBAStatMapping yahoo_mapping = new NBAStatMapping();
		yahoo_mapping.addMapping(0, NBAStatMapping.TEAM);
		yahoo_mapping.addMapping(1, NBAStatMapping.FG_MADE);
		yahoo_mapping.addMapping(2, NBAStatMapping.FG_ATTEMPTED);
		yahoo_mapping.addMapping(3, NBAStatMapping.FG_PERCENTAGE);
		yahoo_mapping.addMapping(5, NBAStatMapping.THREE_PT_MADE);
		yahoo_mapping.addMapping(6, NBAStatMapping.THREE_PT_ATTEMPTED);
		yahoo_mapping.addMapping(7, NBAStatMapping.THREE_PT_PERCENTAGE);
		yahoo_mapping.addMapping(9, NBAStatMapping.FT_MADE);
		yahoo_mapping.addMapping(10, NBAStatMapping.FT_ATTEMPTED);
		yahoo_mapping.addMapping(11, NBAStatMapping.FT_PERCENTAGE);
		yahoo_mapping.addMapping(13, NBAStatMapping.OFF_REBOUNDS);
		yahoo_mapping.addMapping(14, NBAStatMapping.DEF_REBOUNDS);
		yahoo_mapping.addMapping(15, NBAStatMapping.TOTAL_REBOUNDS);
		yahoo_mapping.addMapping(17, NBAStatMapping.ASSISTS);
		yahoo_mapping.addMapping(18, NBAStatMapping.TURNOVERS);
		yahoo_mapping.addMapping(19, NBAStatMapping.STEALS);
		yahoo_mapping.addMapping(20, NBAStatMapping.BLOCKS);
		yahoo_mapping.addMapping(21, NBAStatMapping.FOULS);
		yahoo_mapping.addMapping(22, NBAStatMapping.PTS_PER_GAME);

		for (int j = endYear; j >= startYear; j--) {
			String year_url = yahoo_url.replaceAll("year=season_\\d\\d\\d\\d", "year=season_" + j);
			//System.out.println(year_url);
			
			NBAPageSource yahoo_source = new NBAPageSource(year_url, j, false, yahoo_mapping, "tr[class^=ysprow]");
			list.add(yahoo_source);
		}

		System.out.println("Sources initialized.");
		
		return list;
	}
}
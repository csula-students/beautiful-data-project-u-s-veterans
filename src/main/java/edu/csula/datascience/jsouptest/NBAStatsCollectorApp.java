package edu.csula.datascience.jsouptest;

import java.io.IOException;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

		// to test list of objects us the code below:
		// System.out.println("regular season arraylist: " +
		// regularSeasonStats.size());
		//
		// for (int i = 0; i < regularSeasonStats.size(); i++) {
		// System.out.print(regularSeasonStats.get(i).getField_goal_percentage()
		// + "\n");
		// }
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
		NBAPageSource cbssports_source = new NBAPageSource(cbs_url, cbs_year, cbs_mapping);

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
			NBAPageSource de_source = new NBAPageSource(year_url, i, draftExpress_mapping);
			list.add(de_source);
		}

		// ESPN
		startYear = 1999;
		endYear = 2015;
		String espn_url = "http://espn.go.com/nba/statistics/team/_/stat/team-comparison-per-game/sort/avgPoints/year/2015/seasontype/2";
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
		espn_mapping.addMapping(10, NBAStatMapping.OFF_REBOUNDS);
		espn_mapping.addMapping(11, NBAStatMapping.DEF_REBOUNDS);
		espn_mapping.addMapping(12, NBAStatMapping.TOTAL_REBOUNDS);
		espn_mapping.addMapping(13, NBAStatMapping.TURNOVERS);
		espn_mapping.addMapping(14, NBAStatMapping.DEF_TURNOVERS);

		for (int i = endYear; i >= startYear; i--) {
			String year_url = espn_url.replaceAll("year/\\d\\d\\d\\d", "year/" + i);
			//System.out.println(year_url);
			NBAPageSource espn_source = new NBAPageSource(year_url, i, espn_mapping);
			list.add(espn_source);
		}

		System.out.println("Sources initialized.");
		
		return list;
	}
}
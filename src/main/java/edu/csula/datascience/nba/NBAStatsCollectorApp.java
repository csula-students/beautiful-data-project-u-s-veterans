package edu.csula.datascience.nba;

import java.util.Collection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class NBAStatsCollectorApp extends TimerTask {

	public final SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	public static final int hour_to_run = 6;
	public static Calendar c = Calendar.getInstance();

	public static void main(String[] args) {
		TimerTask timerTask = new NBAStatsCollectorApp();
		// running timer task as daemon thread
		Timer timer = new Timer(false);
		timerTask.run();
		getNextRun();
		Date d = new Date(c.getTimeInMillis());
		timer.scheduleAtFixedRate(timerTask, d, 1000 * 60 * 60 * 24);

	}

	public static void getNextRun() {
		c = Calendar.getInstance();
		if (c.HOUR_OF_DAY >= hour_to_run) {
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		c.set(Calendar.HOUR_OF_DAY, hour_to_run);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
	}

	@Override
	public void run() {
		System.out.println("Import running at " + f.format(new Date().getTime()));
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
					System.out.println("Source (" + sourceCollection.size() + "): Batch(" + batch.size() + "); "
							+ source.getUrl());
					// save here once mongo is set up
					collector.save(batch);
				}
				
				// sleep for 2 seconds
				Thread.sleep(2000);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		System.out.println("Import completed at " + f.format(new Date().getTime()));
		getNextRun();
		System.out.println("The next run will be " + f.format(c.getTime()));
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
			// System.out.println(year_url);
			NBAPageSource espn_source = new NBAPageSource(year_url, i, false, espn_mapping, "tr");
			list.add(espn_source);
		}

		// Yahoo Sports (2002 - 2015)
		startYear = 2002;
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
			// System.out.println(year_url);

			NBAPageSource yahoo_source = new NBAPageSource(year_url, j, false, yahoo_mapping, "tr[class^=ysprow]");
			list.add(yahoo_source);
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

		// ESPN Postseason (1999 - 2016)
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
			// System.out.println(year_url);
			NBAPageSource espn_source_post = new NBAPageSource(year_url, i, true, espn_mapping_post, "tr");
			list.add(espn_source_post);
		}

		// Yahoo Sports Postseason (2002 - 2015)
		startYear = 2002;
		endYear = 2015;
		String yahoo_url_post = "http://sports.yahoo.com/nba/stats/byteam?cat1=Total&cat2=team&sort=232&conference=NBA&year=postseason_2015";
		NBAStatMapping yahoo_mapping_post = new NBAStatMapping();
		yahoo_mapping_post.addMapping(0, NBAStatMapping.TEAM);
		yahoo_mapping_post.addMapping(1, NBAStatMapping.FG_MADE);
		yahoo_mapping_post.addMapping(2, NBAStatMapping.FG_ATTEMPTED);
		yahoo_mapping_post.addMapping(3, NBAStatMapping.FG_PERCENTAGE);
		yahoo_mapping_post.addMapping(5, NBAStatMapping.THREE_PT_MADE);
		yahoo_mapping_post.addMapping(6, NBAStatMapping.THREE_PT_ATTEMPTED);
		yahoo_mapping_post.addMapping(7, NBAStatMapping.THREE_PT_PERCENTAGE);
		yahoo_mapping_post.addMapping(9, NBAStatMapping.FT_MADE);
		yahoo_mapping_post.addMapping(10, NBAStatMapping.FT_ATTEMPTED);
		yahoo_mapping_post.addMapping(11, NBAStatMapping.FT_PERCENTAGE);
		yahoo_mapping_post.addMapping(13, NBAStatMapping.OFF_REBOUNDS);
		yahoo_mapping_post.addMapping(14, NBAStatMapping.DEF_REBOUNDS);
		yahoo_mapping_post.addMapping(15, NBAStatMapping.TOTAL_REBOUNDS);
		yahoo_mapping_post.addMapping(17, NBAStatMapping.ASSISTS);
		yahoo_mapping_post.addMapping(18, NBAStatMapping.TURNOVERS);
		yahoo_mapping_post.addMapping(19, NBAStatMapping.STEALS);
		yahoo_mapping_post.addMapping(20, NBAStatMapping.BLOCKS);
		yahoo_mapping_post.addMapping(21, NBAStatMapping.FOULS);
		yahoo_mapping_post.addMapping(22, NBAStatMapping.PTS_PER_GAME);

		for (int j = endYear; j >= startYear; j--) {
			String year_url = yahoo_url_post.replaceAll("year=postseason_\\d\\d\\d\\d", "year=postseason_" + j);
			// System.out.println(year_url);

			NBAPageSource yahoo_source_post = new NBAPageSource(year_url, j, true, yahoo_mapping_post,
					"tr[class^=ysprow]");
			list.add(yahoo_source_post);
		}

		// Draft Express Players' Stats(1979-2016)
		startYear = 1979;
		endYear = 2016;
		String draftExpress_players_url = "http://www.draftexpress.com/stats.php?sort=&q=&league=NBA&year=2016&per=pergame&min=All&pos=all&qual=prospects";
		NBAStatMapping draftExpress_players_mapping = new NBAStatMapping();
		draftExpress_players_mapping.addMapping(1, NBAStatMapping.PLAYER_NAME);
		draftExpress_players_mapping.addMapping(2, NBAStatMapping.TEAM);
		draftExpress_players_mapping.addMapping(3, NBAStatMapping.GAMES_PLAYED);
		draftExpress_players_mapping.addMapping(4, NBAStatMapping.MIN_PLAYED);
		draftExpress_players_mapping.addMapping(5, NBAStatMapping.PTS_PER_GAME);
		draftExpress_players_mapping.addMapping(6, NBAStatMapping.FG_MADE);
		draftExpress_players_mapping.addMapping(7, NBAStatMapping.FG_ATTEMPTED);
		draftExpress_players_mapping.addMapping(8, NBAStatMapping.FG_PERCENTAGE);
		draftExpress_players_mapping.addMapping(9, NBAStatMapping.TWO_PT_MADE);
		draftExpress_players_mapping.addMapping(10, NBAStatMapping.TWO_PT_ATTEMPTED);
		draftExpress_players_mapping.addMapping(11, NBAStatMapping.TWO_PT_PERCENTAGE);
		draftExpress_players_mapping.addMapping(12, NBAStatMapping.THREE_PT_MADE);
		draftExpress_players_mapping.addMapping(13, NBAStatMapping.THREE_PT_ATTEMPTED);
		draftExpress_players_mapping.addMapping(14, NBAStatMapping.THREE_PT_PERCENTAGE);
		draftExpress_players_mapping.addMapping(15, NBAStatMapping.FT_MADE);
		draftExpress_players_mapping.addMapping(16, NBAStatMapping.FT_ATTEMPTED);
		draftExpress_players_mapping.addMapping(17, NBAStatMapping.FT_PERCENTAGE);
		draftExpress_players_mapping.addMapping(18, NBAStatMapping.OFF_REBOUNDS);
		draftExpress_players_mapping.addMapping(19, NBAStatMapping.DEF_REBOUNDS);
		draftExpress_players_mapping.addMapping(20, NBAStatMapping.TOTAL_REBOUNDS);
		draftExpress_players_mapping.addMapping(21, NBAStatMapping.ASSISTS);
		draftExpress_players_mapping.addMapping(22, NBAStatMapping.STEALS);
		draftExpress_players_mapping.addMapping(23, NBAStatMapping.BLOCKS);
		draftExpress_players_mapping.addMapping(24, NBAStatMapping.TURNOVERS);
		draftExpress_players_mapping.addMapping(25, NBAStatMapping.FOULS);

		for (int i = endYear; i >= startYear; i--) {
			String year_url = draftExpress_players_url.replaceAll("year=\\d\\d\\d\\d", "year=" + i);
			// System.out.println(year_url);
			NBAPageSource draftExpress_source = new NBAPageSource(year_url, i, false, draftExpress_players_mapping,
					"tr");
			list.add(draftExpress_source);
		}

		System.out.println("Sources initialized.");

		return list;
	}
}
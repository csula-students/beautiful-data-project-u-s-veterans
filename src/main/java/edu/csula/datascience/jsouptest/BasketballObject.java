package edu.csula.datascience.jsouptest;

public class BasketballObject {
	
	private String team;
	private Integer year;
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
	private double def_field_goal_percentage;
	private double point_difference;
	private double def_three_points_percentage;
	private double def_turnovers;
	
	public BasketballObject() {
	}
	
	public BasketballObject(String team, Integer year, double games_played,
			double points_per_game, double field_goal_made,
			double field_goal_attempted, double field_goal_percentage,
			double two_points_made, double two_points_attempted, double two_points_percentage,
			double three_points_made, double three_points_attempted,
			double three_points_percentage, double free_throws_made,
			double free_throws_attempted, double free_throws_percentage,
			double offensive_rebounds, double defensive_rebounds,
			double total_rebounds, double assists, double steals,
			double blocks, double turnovers, double fouls,
			double def_points_per_game, double def_field_goal_percentage,
			double point_difference, double def_three_points_percentage,
			double def_turnovers) {
		this.team = team;
		this.year = year;
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
		this.def_field_goal_percentage = def_field_goal_percentage;
		this.point_difference = point_difference;
		this.def_three_points_percentage = def_three_points_percentage;
		this.def_turnovers = def_turnovers;
	}
	
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public Integer getYear(){
		return year;
	}
	public void setYear(Integer year){
		this.year = year;
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
	public double getTwo_points_percentage(){
		return two_points_percentage;
	}
	public void setTwo_points_percentage(double two_points_percentage){
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
		this.defensive_rebounds= defensive_rebounds;
	}
	public double getDefensive_rebounds() {
		return defensive_rebounds;
	}
	public void setTotal_rebounds(double total_rebounds) {
		this.total_rebounds= total_rebounds;
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
	public void setDef_points_per_game(double def_points_per_game){
		this.def_points_per_game = def_points_per_game;
	}
	public double getDef_points_per_game(){
		return def_points_per_game;
	}
	public void setDef_field_goal_percentage(double def_field_goal_percentage){
		this.def_field_goal_percentage = def_field_goal_percentage;
	}
	public double getDef_field_goal_percentage(){
		return def_field_goal_percentage;
	}
	public void setPoint_difference(double point_difference){
		this.point_difference = point_difference;
	}
	public double getPoint_difference(){
		return point_difference;
	}
	public void setDef_three_points_percentage(double def_three_points_percentage){
		this.def_three_points_percentage = def_three_points_percentage;
	}
	public double getDef_three_points_percentage(){
		return def_three_points_percentage;
	}
	public void setDef_turnovers(double def_turnovers){
		this.def_turnovers = def_turnovers;
	}
	public double getDef_turnovers(){
		return def_turnovers;
	}
}

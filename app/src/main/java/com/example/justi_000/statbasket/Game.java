package com.example.justi_000.statbasket;


public class Game
{
    long game_id;
    long home_team_id;
    long away_team_id;
    String location;
    String created_date;
    //GameStats has a reference to game_id

    public Game(long homeTeamId, long awayTeamId, String gameLocation)
    {
        home_team_id = homeTeamId;
        away_team_id = awayTeamId;
        location = gameLocation;
        created_date = "";
    }

    public Game(long homeTeamId)
    {
        home_team_id = homeTeamId;
        away_team_id = 0;
        location = "";
        created_date = "";
    }

    public Game()
    {
        game_id = 0;
        home_team_id = 0;
        away_team_id = 0;
        location = "";
        created_date = "";
    }

    public void setGameId(long gameId) { this.game_id = gameId; }
    public void setHomeTeamId(long homeTeamId) { this.home_team_id = homeTeamId; }
    public void setAwayTeamId(long awayTeamId) { this.away_team_id = awayTeamId; }
    public void setLocation(String location) { this.location = location; }
    public void setCreatedDate(String created_date) { this.created_date = created_date; }

    // getters
    public long getGameId() {
        return this.game_id;
    }
    public long getHomeTeamId() { return this.home_team_id; }
    public long getAwayTeamId() { return this.away_team_id; }
    public String getLocation() { return this.location; }
    public String getCreatedDate() { return this.created_date; }
}

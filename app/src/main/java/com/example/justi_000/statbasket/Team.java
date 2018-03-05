package com.example.justi_000.statbasket;


public class Team
{
    long team_id;
    String team_name;
    String created_date;

    // constructors
    public Team()
    {
        team_id = 0;
        team_name = "";
        created_date = "";
    }

    public Team(String name) {
        this.team_name = name;
    }

    public Team(long id, String name) {
        this.team_id = id;
        this.team_name = name;
    }

    // setters
    public void setId(long id) {
        this.team_id = id;
    }

    public void setName(String name) {
        this.team_name = name;
    }

    public void setCreatedDate(String created_date) {
        this.created_date = created_date;
    }

    // getters
    public long getId() {
        return this.team_id;
    }

    public String getName() {
        return this.team_name;
    }

    public String getCreatedDate() {
        return this.created_date;
    }
}
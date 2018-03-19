package com.example.justi_000.statbasket;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class GameListActivity extends AppCompatActivity implements View.OnClickListener
{
    DbHelper myDatabase;
    Bundle bundle = new Bundle();
    ListView lvGameList;
    List<Game> games;
    List<String> game_names;
    Team team;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        myDatabase = new DbHelper(this);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        bundle = getIntent().getExtras();
        if(bundle != null) {
            team = myDatabase.getTeam(bundle.getLong("team_id", 0));
        }
//        Team team = new Team(bundle.getLong("team_id"), bundle.getString("team_name"));
//        Team team = myDatabase.getTeam(bundle.getLong("team_id", -1));
//        if (team == null)
//        {
//            team = myDatabase.getTeam(bundle.getLong("team_id", -1));
//        }
//        else if (team.getId() <= 0)
//        {
//            team = myDatabase.getTeam(bundle.getLong("team_id", -1));
//        }
        if (team.getId() > 0)
        {
            setTitle(team.getName());
            Game tempGame = new Game(1,2,"KC");
            long team_id = myDatabase.createGame(tempGame);
            games = myDatabase.getAllGames(team.getId());
        }
        game_names = new ArrayList<>();
        for(Game game : games)
        {
            Team homeTeam = myDatabase.getTeam(game.getHomeTeamId());
            Team awayTeam = myDatabase.getTeam(game.getAwayTeamId());
            String game_name = "";
            if (homeTeam.getName().length() > 0)
                game_name += homeTeam.getName();
            if (awayTeam.getName().length() > 0)
                game_name = game_name + " vs. " + awayTeam.getName();
            game_names.add(game_name);
        }

        lvGameList.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, game_names));

        lvGameList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent i = new Intent(GameListActivity.this, ViewGameActivity.class);
                bundle.putLong("game_id", games.get(position).getGameId());
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View v)
    {
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_team_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.add_player:
                Intent i = new Intent(GameListActivity.this, ViewGameActivity.class);
                bundle.putString("team_id", "");
                i.putExtras(bundle);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

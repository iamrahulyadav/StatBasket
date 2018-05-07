package com.example.justi_000.statbasket;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class ViewStatsActivity extends AppCompatActivity implements View.OnClickListener
{
    DbHelper myDatabase;
    Bundle bundle = new Bundle();
    Team team;
    Game game;
    List<Player> playerList;

    TableLayout tlStats;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stats);

        tlStats = findViewById(R.id.tl_stats);
        tlStats.setStretchAllColumns(true);

        myDatabase = new DbHelper(this);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        bundle = getIntent().getExtras();
        if(bundle != null) {
            team = myDatabase.getTeam(bundle.getLong("team_id", 0));
            game = myDatabase.getGame(bundle.getLong("game_id", 0));
        }

        playerList = myDatabase.getAllPlayers(team.getId());

        long count = 0;
        long num = 0;
        for (Player player : playerList)
        {
            count++;
            TableRow tr = new TableRow(this);
            if (count % 2 != 0)
            {
                tr.setBackgroundColor(Color.GRAY);
            }
            tr.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT));

            TextView tv1 = new TextView(this);
            tv1.setText(player.getLastName());
            tr.addView(tv1);

            TextView tv2 = new TextView(this);
            num = myDatabase.getStat(player.getId(), game.getGameId(), "made_one");
            tv2.setText(String.valueOf(num));
            tr.addView(tv2);

            TextView tv3 = new TextView(this);
            num = myDatabase.getStat(player.getId(), game.getGameId(), "missed_one");
            tv3.setText(String.valueOf(num));
            tr.addView(tv3);

            TextView tv4 = new TextView(this);
            num = myDatabase.getStat(player.getId(), game.getGameId(), "made_two");
            tv4.setText(String.valueOf(num));
            tr.addView(tv4);

            TextView tv5 = new TextView(this);
            num = myDatabase.getStat(player.getId(), game.getGameId(), "missed_two");
            tv5.setText(String.valueOf(num));
            tr.addView(tv5);

            TextView tv6 = new TextView(this);
            num = myDatabase.getStat(player.getId(), game.getGameId(), "made_three");
            tv6.setText(String.valueOf(num));
            tr.addView(tv6);

            TextView tv7 = new TextView(this);
            num = myDatabase.getStat(player.getId(), game.getGameId(), "missed_three");
            tv7.setText(String.valueOf(num));
            tr.addView(tv7);

            TextView tv8 = new TextView(this);
            num = myDatabase.getStat(player.getId(), game.getGameId(), "def_reb");
            tv8.setText(String.valueOf(num));
            tr.addView(tv8);

            TextView tv9 = new TextView(this);
            num = myDatabase.getStat(player.getId(), game.getGameId(), "off_reb");
            tv9.setText(String.valueOf(num));
            tr.addView(tv9);

            TextView tv10 = new TextView(this);
            num = myDatabase.getStat(player.getId(), game.getGameId(), "assist");
            tv10.setText(String.valueOf(num));
            tr.addView(tv10);

            TextView tv11 = new TextView(this);
            num = myDatabase.getStat(player.getId(), game.getGameId(), "steal");
            tv11.setText(String.valueOf(num));
            tr.addView(tv11);

            TextView tv12 = new TextView(this);
            num = myDatabase.getStat(player.getId(), game.getGameId(), "turnover");
            tv12.setText(String.valueOf(num));
            tr.addView(tv12);

            TextView tv13 = new TextView(this);
            num = myDatabase.getStat(player.getId(), game.getGameId(), "block");
            tv13.setText(String.valueOf(num));
            tr.addView(tv13);

            TextView tv14 = new TextView(this);
            num = myDatabase.getStat(player.getId(), game.getGameId(), "foul");
            tv14.setText(String.valueOf(num));
            tr.addView(tv14);

            tlStats.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }

        Team homeTeam = myDatabase.getTeam(game.getHomeTeamId());
        Team awayTeam = myDatabase.getTeam(game.getAwayTeamId());
        String game_name = "";
        if (homeTeam.getName().length() > 0)
            game_name += homeTeam.getName();
        if (awayTeam.getName().length() > 0)
            game_name = game_name + " vs. " + awayTeam.getName();
        setTitle(game_name);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.home:
                startActivity(new Intent(ViewStatsActivity.this, MainActivity.class));
                return true;
            case R.id.new_game:
                Intent i = new Intent(ViewStatsActivity.this, ViewGameActivity.class);
                bundle.putLong("team_id", team.getId());
                i.putExtras(bundle);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {

    }
}

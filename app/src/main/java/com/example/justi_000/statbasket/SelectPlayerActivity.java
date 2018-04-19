package com.example.justi_000.statbasket;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SelectPlayerActivity extends AppCompatActivity implements View.OnClickListener
{
    DbHelper myDatabase;
    Bundle bundle = new Bundle();
    List<Player> active_players = new ArrayList<>();
    List<String> active_player_names = new ArrayList<>();
    Team team;
    Game game;
    ArrayAdapter<String> arrayAdapter;
    String currentStat;

    Button btnCancel;
    ListView lvPlayerList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_player);

        lvPlayerList = findViewById(R.id.lv_item_list);
        btnCancel = findViewById(R.id.btn_cancel);

        myDatabase = new DbHelper(this);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, active_player_names);
        lvPlayerList.setAdapter(arrayAdapter);

        cancel();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        bundle = getIntent().getExtras();
        if(bundle != null) {
            team = myDatabase.getTeam(bundle.getLong("team_id", 0));
            game = myDatabase.getGame(bundle.getLong("game_id", 0));
            currentStat = bundle.getString("stat", "");
        }

        if (game.getGameId() > 0) {
            active_players = myDatabase.getActivePlayers(game.getGameId());
        }

        setTitle("Select Player");

        for (Player player : active_players) {
            active_player_names.add(player.getFirstName() + " " + player.getLastName());
        }

        lvPlayerList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
//                Intent i = new Intent(SelectPlayerActivity.this, GameActivity.class);
//                bundle.putLong("player_id", active_players.get(position).getId());
//                bundle.putLong("game_id", game.getGameId());
//                bundle.putLong("team_id", team.getId());
//                i.putExtras(bundle);
//                startActivity(i);

                Intent i = new Intent();
                bundle.putLong("player_id", active_players.get(position).getId());
                bundle.putLong("game_id", game.getGameId());
                bundle.putLong("team_id", team.getId());
                bundle.putString("stat", currentStat);
                i.putExtras(bundle);
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });
    }

    public void cancel()
    {
        btnCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {

                        Intent i = new Intent();
                        bundle.putLong("player_id", 0);
                        bundle.putLong("game_id", game.getGameId());
                        bundle.putLong("team_id", team.getId());
                        bundle.putString("stat", "cancel");
                        i.putExtras(bundle);
                        setResult(Activity.RESULT_OK, i);
                        finish();
//                        Intent i = new Intent(SelectPlayerActivity.this, GameActivity.class);
//                        bundle.putLong("game_id", game.getGameId());
//                        bundle.putLong("team_id", team.getId());
//                        bundle.putLong("player_id", 0);
//                        i.putExtras(bundle);
//                        startActivity(i);
                    }
                }
        );
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
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
                startActivity(new Intent(SelectPlayerActivity.this, MainActivity.class));
                return true;
            case R.id.new_game:
                Intent i = new Intent(SelectPlayerActivity.this, ViewGameActivity.class);
                bundle.putLong("team_id", team.getId());
                i.putExtras(bundle);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v)
    {
    }
}






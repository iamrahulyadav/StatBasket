package com.example.justi_000.statbasket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SubPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    DbHelper myDatabase;
    Bundle bundle = new Bundle();
    List<Player> players = new ArrayList<>();
    List<String> player_names = new ArrayList<>();
    List<Player> active_players = new ArrayList<>();
    List<String> active_player_names = new ArrayList<>();
    Team team;
    Game game;
    ArrayAdapter activePlayersArrayAdapter;
    ArrayAdapter benchPlayersArrayAdapter;

    ListView lvActivePlayers;
    ListView lvBenchPlayers;
    Button btnConfirm;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_player);

        myDatabase = new DbHelper(this);

        lvActivePlayers = findViewById(R.id.lv_active_players);
        lvBenchPlayers = findViewById(R.id.lv_bench_players);
        btnConfirm = findViewById(R.id.btn_confirm);
        btnCancel = findViewById(R.id.btn_cancel);

        confirm();
        cancel();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            team = myDatabase.getTeam(bundle.getLong("team_id", 0));
            game = myDatabase.getGame(bundle.getLong("game_id", 0));
        }

        if (team.getId() > 0)
        {
            setTitle(team.getName());
            players = myDatabase.getAllPlayers(team.getId());
        }

        if (game.getGameId() > 0)
        {
            active_players = myDatabase.getActivePlayers(game.getGameId());
        }

        for(Player player : players)
        {
            player_names.add(player.getFirstName() + " " + player.getLastName());
        }

        activePlayersArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, active_player_names);
        benchPlayersArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, player_names);

        lvActivePlayers.setAdapter(activePlayersArrayAdapter);
        lvBenchPlayers.setAdapter(benchPlayersArrayAdapter);

        for (Player player : active_players)
        {
            int position = benchPlayersArrayAdapter.getPosition(player.getFirstName() + " " + player.getLastName());

            if (position >= 0) {
                active_player_names.add(String.valueOf(benchPlayersArrayAdapter.getItem(position)));

                players.remove(position);
                player_names.remove(position);

                activePlayersArrayAdapter.notifyDataSetChanged();
                benchPlayersArrayAdapter.notifyDataSetChanged();
            }
        }

        lvActivePlayers.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                players.add(active_players.get(position));
                player_names.add(active_player_names.get(position));

                active_players.remove(position);
                active_player_names.remove(position);


                activePlayersArrayAdapter.notifyDataSetChanged();
                benchPlayersArrayAdapter.notifyDataSetChanged();
            }
        });

        lvBenchPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                active_players.add(players.get(position));
                active_player_names.add(player_names.get(position));

                players.remove(position);
                player_names.remove(position);

                activePlayersArrayAdapter.notifyDataSetChanged();
                benchPlayersArrayAdapter.notifyDataSetChanged();
            }
        });
    }

    public void confirm()
    {
        btnConfirm.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public  void onClick(View v)
                    {
                        if (game.getGameId() > 0)
                        {
                            //add success check here for updating????
//                            myDatabase.updateActivePlayers(game.getGameId(), active_players);
                            myDatabase.createActivePlayers(game.getGameId(), active_players);
                            Intent intent = new Intent(SubPlayerActivity.this, GameActivity.class);
                            bundle.putLong("team_id", team.getId());
                            bundle.putLong("game_id", game.getGameId());
                            intent.putExtras(bundle);
                            startActivity(intent);
//                            else {
//                                Toast.makeText(SubPlayerActivity.this, "Substitution Failed", Toast.LENGTH_LONG).show();
//                            }
                        }
                    }
                });
    }

    public void cancel()
    {
        btnCancel.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public  void onClick(View v)
                    {
                        if (game.getGameId() > 0)
                        {
                            Intent intent = new Intent(SubPlayerActivity.this, GameActivity.class);
                            bundle.putLong("team_id", team.getId());
                            bundle.putLong("game_id", game.getGameId());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {

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
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.new_game:
                Intent i = new Intent(this, ViewGameActivity.class);
                bundle.putLong("team_id", team.getId());
                i.putExtras(bundle);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
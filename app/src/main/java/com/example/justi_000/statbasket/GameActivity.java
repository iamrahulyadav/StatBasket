package com.example.justi_000.statbasket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class GameActivity extends AppCompatActivity implements View.OnClickListener
{
    DbHelper myDatabase;
    Bundle bundle = new Bundle();
    Team team;
    Game game;
    Player currentPlayer;
    String currentStat;
    ArrayList<String> gameLog = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
//    List<Player> active_players = new ArrayList<>();

    Button btnOneMake;
    Button btnOneMiss;
    Button btnTwoMake;
    Button btnTwoMiss;
    Button btnThreeMake;
    Button btnThreeMiss;
    Button btnDefReb;
    Button btnOffReb;
    Button btnTurnover;
    Button btnSteal;
    Button btnAssist;
    Button btnBlock;
    Button btnFoul;
    ListView lvGameLog;
    Button btnSub;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        btnOneMake = findViewById(R.id.btn_one_make);
        btnOneMiss = findViewById(R.id.btn_one_miss);
        btnTwoMake = findViewById(R.id.btn_two_make);
        btnTwoMiss = findViewById(R.id.btn_two_miss);
        btnThreeMake = findViewById(R.id.btn_three_make);
        btnThreeMiss = findViewById(R.id.btn_three_miss);
        btnDefReb = findViewById(R.id.btn_dreb);
        btnOffReb = findViewById(R.id.btn_oreb);
        btnTurnover = findViewById(R.id.btn_to);
        btnSteal = findViewById(R.id.btn_stl);
        btnAssist = findViewById(R.id.btn_asst);
        btnBlock = findViewById(R.id.btn_blk);
        lvGameLog = findViewById(R.id.lv_game_log);
        btnSub = findViewById(R.id.btn_cancel);
        btnFoul = findViewById(R.id.btn_foul);

        myDatabase = new DbHelper(this);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gameLog);
        lvGameLog.setAdapter(arrayAdapter);

        addOne();
        subtractOne();
        addTwo();
        subtractTwo();
        addThree();
        subtractThree();
        addOffensiveRebound();
        addDefensiveRebound();
        addAssist();
        addSteal();
        addTurnover();
        addBlock();
        addFoul();
        subPlayers();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        bundle = getIntent().getExtras();
        if(bundle != null) {
            team = myDatabase.getTeam(bundle.getLong("team_id", 0));
            game = myDatabase.getGame(bundle.getLong("game_id", 0));
            currentPlayer = myDatabase.getPlayer(bundle.getLong("player_id", 0));
            currentStat = bundle.getString("stat", "");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        bundle = data.getExtras();
        currentStat = bundle.getString("stat", "");
        currentPlayer = myDatabase.getPlayer(bundle.getLong("player_id", 0));
        long success;
        if (currentPlayer.getId() > 0) {
            if (requestCode == 1) {
                if (resultCode == Activity.RESULT_OK) {
                    switch (currentStat) {
                        case "made_one":
                            success = myDatabase.insertStat(game.getGameId(), currentPlayer.getId(), currentStat);
                            if (success > 0){
                                gameLog.add(0, "1PT - " + currentPlayer.getLastName() + " #" + currentPlayer.getNumber());
                                arrayAdapter.notifyDataSetChanged();
                            }
                            break;
                        case "missed_one":
                            success = myDatabase.insertStat(game.getGameId(), currentPlayer.getId(),
                                    );
                            if (success > 0) {
                                gameLog.add(0, "1PT Miss - " + currentPlayer.getLastName() + " #" + currentPlayer.getNumber());
                                arrayAdapter.notifyDataSetChanged();
                            }
                            break;
                        case "made_two":
                            success = myDatabase.insertStat(game.getGameId(), currentPlayer.getId(), currentStat);
                            if (success > 0) {
                                gameLog.add(0, "2PT - " + currentPlayer.getLastName() + " #" + currentPlayer.getNumber());
                                arrayAdapter.notifyDataSetChanged();
                            }
                            break;
                        case "missed_two":
                            success = myDatabase.insertStat(game.getGameId(), currentPlayer.getId(), currentStat);
                            if (success > 0) {
                                gameLog.add(0, "2PT Miss - " + currentPlayer.getLastName() + " #" + currentPlayer.getNumber());
                                arrayAdapter.notifyDataSetChanged();
                            }
                            break;
                        case "made_three":
                            success = myDatabase.insertStat(game.getGameId(), currentPlayer.getId(), currentStat);
                            if (success > 0) {
                                gameLog.add(0, "3PT - " + currentPlayer.getLastName() + " #" + currentPlayer.getNumber());
                                arrayAdapter.notifyDataSetChanged();
                            }
                            break;
                        case "missed_three":
                            success = myDatabase.insertStat(game.getGameId(), currentPlayer.getId(), currentStat);
                            if (success > 0) {
                                gameLog.add(0, "3PT Miss - " + currentPlayer.getLastName() + " #" + currentPlayer.getNumber());
                                arrayAdapter.notifyDataSetChanged();
                            }
                            break;
                        case "def_reb":
                            success = myDatabase.insertStat(game.getGameId(), currentPlayer.getId(), currentStat);
                            if (success > 0) {
                                gameLog.add(0, "Def Reb - " + currentPlayer.getLastName() + " #" + currentPlayer.getNumber());
                                arrayAdapter.notifyDataSetChanged();
                            }
                            break;
                        case "off_reb":
                            success = myDatabase.insertStat(game.getGameId(), currentPlayer.getId(), currentStat);
                            if (success > 0) {
                                gameLog.add(0, "Off Reb - " + currentPlayer.getLastName() + " #" + currentPlayer.getNumber());
                                arrayAdapter.notifyDataSetChanged();
                            }
                            break;
                        case "assist":
                            success = myDatabase.insertStat(game.getGameId(), currentPlayer.getId(), currentStat);
                            if (success > 0) {
                                gameLog.add(0, "Assist - " + currentPlayer.getLastName() + " #" + currentPlayer.getNumber());
                                arrayAdapter.notifyDataSetChanged();
                            }
                            break;
                        case "steal":
                            success = myDatabase.insertStat(game.getGameId(), currentPlayer.getId(), currentStat);
                            if (success > 0) {
                                gameLog.add(0, "Steal - " + currentPlayer.getLastName() + " #" + currentPlayer.getNumber());
                                arrayAdapter.notifyDataSetChanged();
                            }
                            break;
                        case "turnover":
                            success = myDatabase.insertStat(game.getGameId(), currentPlayer.getId(), currentStat);
                            if (success > 0) {
                                gameLog.add(0, "Turnover - " + currentPlayer.getLastName() + " #" + currentPlayer.getNumber());
                                arrayAdapter.notifyDataSetChanged();
                            }
                            break;
                        case "block":
                            success = myDatabase.insertStat(game.getGameId(), currentPlayer.getId(), currentStat);
                            if (success > 0) {
                                gameLog.add(0, "Block - " + currentPlayer.getLastName() + " #" + currentPlayer.getNumber());
                                arrayAdapter.notifyDataSetChanged();
                            }
                            break;
                        case "foul":
                            success = myDatabase.insertStat(game.getGameId(), currentPlayer.getId(), currentStat);
                            if (success > 0) {
                                gameLog.add(0, "Foul - " + currentPlayer.getLastName() + " #" + currentPlayer.getNumber());
                                arrayAdapter.notifyDataSetChanged();
                            }
                            break;
                        default:
                            gameLog.add(0, "SOMETHING FUNKY HAPPENED");
                            break;
                    }
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    gameLog.add(0, "...cancelled");
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    public void addOne()
    {
        btnOneMake.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(GameActivity.this, SelectPlayerActivity.class);
                        bundle.putLong("game_id", game.getGameId());
                        bundle.putLong("team_id", team.getId());
                        bundle.putString("stat", "made_one");
                        i.putExtras(bundle);
                        startActivityForResult(i, 1);

//                        gameLog.add(0, "...made free throw");
//                        arrayAdapter.notifyDataSetChanged();
                    }
                }
        );
    }

    public void subtractOne()
    {
        btnOneMiss.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(GameActivity.this, SelectPlayerActivity.class);
                        bundle.putLong("game_id", game.getGameId());
                        bundle.putLong("team_id", team.getId());
                        bundle.putString("stat", "missed_one");
                        i.putExtras(bundle);
                        startActivityForResult(i, 1);
                    }
                }
        );
    }

    public void addTwo()
    {
        btnTwoMake.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(GameActivity.this, SelectPlayerActivity.class);
                        bundle.putLong("game_id", game.getGameId());
                        bundle.putLong("team_id", team.getId());
                        bundle.putString("stat", "made_two");
                        i.putExtras(bundle);
                        startActivityForResult(i, 1);
                    }
                }
        );
    }

    public void subtractTwo()
    {
        btnTwoMiss.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(GameActivity.this, SelectPlayerActivity.class);
                        bundle.putLong("game_id", game.getGameId());
                        bundle.putLong("team_id", team.getId());
                        bundle.putString("stat", "missed_two");
                        i.putExtras(bundle);
                        startActivityForResult(i, 1);
                    }
                }
        );
    }

    public void addThree()
    {
        btnThreeMake.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(GameActivity.this, SelectPlayerActivity.class);
                        bundle.putLong("game_id", game.getGameId());
                        bundle.putLong("team_id", team.getId());
                        bundle.putString("stat", "made_three");
                        i.putExtras(bundle);
                        startActivityForResult(i, 1);
                    }
                }
        );
    }

    public void subtractThree()
    {
        btnThreeMiss.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(GameActivity.this, SelectPlayerActivity.class);
                        bundle.putLong("game_id", game.getGameId());
                        bundle.putLong("team_id", team.getId());
                        bundle.putString("stat", "missed_three");
                        i.putExtras(bundle);
                        startActivityForResult(i, 1);
                    }
                }
        );
    }

    public void addOffensiveRebound()
    {
        btnOffReb.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(GameActivity.this, SelectPlayerActivity.class);
                        bundle.putLong("game_id", game.getGameId());
                        bundle.putLong("team_id", team.getId());
                        bundle.putString("stat", "off_reb");
                        i.putExtras(bundle);
                        startActivityForResult(i, 1);
                    }
                }
        );
    }

    public void addDefensiveRebound()
    {
        btnDefReb.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(GameActivity.this, SelectPlayerActivity.class);
                        bundle.putLong("game_id", game.getGameId());
                        bundle.putLong("team_id", team.getId());
                        bundle.putString("stat", "def_reb");
                        i.putExtras(bundle);
                        startActivityForResult(i, 1);
                    }
                }
        );
    }

    public void addSteal()
    {
        btnSteal.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(GameActivity.this, SelectPlayerActivity.class);
                        bundle.putLong("game_id", game.getGameId());
                        bundle.putLong("team_id", team.getId());
                        bundle.putString("stat", "steal");
                        i.putExtras(bundle);
                        startActivityForResult(i, 1);
                    }
                }
        );
    }

    public void addAssist()
    {
        btnAssist.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(GameActivity.this, SelectPlayerActivity.class);
                        bundle.putLong("game_id", game.getGameId());
                        bundle.putLong("team_id", team.getId());
                        bundle.putString("stat", "assist");
                        i.putExtras(bundle);
                        startActivityForResult(i, 1);
                    }
                }
        );
    }

    public void addTurnover()
    {
        btnTurnover.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(GameActivity.this, SelectPlayerActivity.class);
                        bundle.putLong("game_id", game.getGameId());
                        bundle.putLong("team_id", team.getId());
                        bundle.putString("stat", "turnover");
                        i.putExtras(bundle);
                        startActivityForResult(i, 1);
                    }
                }
        );
    }

    public void addBlock()
    {
        btnBlock.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(GameActivity.this, SelectPlayerActivity.class);
                        bundle.putLong("game_id", game.getGameId());
                        bundle.putLong("team_id", team.getId());
                        bundle.putString("stat", "block");
                        i.putExtras(bundle);
                        startActivityForResult(i, 1);
                    }
                }
        );
    }

    public void addFoul()
    {
        btnFoul.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent i = new Intent(GameActivity.this, SelectPlayerActivity.class);
                        bundle.putLong("game_id", game.getGameId());
                        bundle.putLong("team_id", team.getId());
                        bundle.putString("stat", "foul");
                        i.putExtras(bundle);
                        startActivityForResult(i, 1);
                    }
                }
        );
    }

    public void subPlayers()
    {
        btnSub.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(GameActivity.this, SubPlayerActivity.class);
                        bundle.putLong("game_id", game.getGameId());
                        bundle.putLong("team_id", team.getId());
                        intent.putExtras(bundle);
                        startActivity(intent);
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
                startActivity(new Intent(GameActivity.this, MainActivity.class));
                return true;
            case R.id.new_game:
                Intent i = new Intent(GameActivity.this, ViewGameActivity.class);
                bundle.putLong("team_id", team.getId());
                i.putExtras(bundle);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public void onClick(View view)
    {

    }
}

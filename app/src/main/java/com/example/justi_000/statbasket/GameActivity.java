package com.example.justi_000.statbasket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class GameActivity extends AppCompatActivity implements View.OnClickListener
{
    DbHelper myDatabase;
    Bundle bundle = new Bundle();
    Team team;
    Game game;
    ArrayList<String> gameLog = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

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
        btnSub = findViewById(R.id.btn_sub);

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

    public void addOne()
    {
        btnOneMake.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
//                        listItems.add("Clicked : "+clickCounter++);
//                        adapter.notifyDataSetChanged();
                        gameLog.add(0, "...made free throw");
                        arrayAdapter.notifyDataSetChanged();
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
//                        public void addItems(View v)
//                        listItems.add("Clicked : "+clickCounter++);
//                        adapter.notifyDataSetChanged();
                        gameLog.add(0, "...missed free throw");
                        arrayAdapter.notifyDataSetChanged();
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
//                        public void addItems(View v)
//                        listItems.add("Clicked : "+clickCounter++);
//                        adapter.notifyDataSetChanged();
                        gameLog.add(0, "...made 2pt");
                        arrayAdapter.notifyDataSetChanged();
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
//                        public void addItems(View v)
//                        listItems.add("Clicked : "+clickCounter++);
//                        adapter.notifyDataSetChanged();
                        gameLog.add(0, "...missed 2pt");
                        arrayAdapter.notifyDataSetChanged();
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
//                        public void addItems(View v)
//                        listItems.add("Clicked : "+clickCounter++);
//                        adapter.notifyDataSetChanged();
                        gameLog.add(0, "...made 3pt");
                        arrayAdapter.notifyDataSetChanged();
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
//                        public void addItems(View v)
//                        listItems.add("Clicked : "+clickCounter++);
//                        adapter.notifyDataSetChanged();
                        gameLog.add(0, "...missed 3pt");
                        arrayAdapter.notifyDataSetChanged();
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
//                        public void addItems(View v)
//                        listItems.add("Clicked : "+clickCounter++);
//                        adapter.notifyDataSetChanged();
                        gameLog.add(0, "...O-Reb");
                        arrayAdapter.notifyDataSetChanged();
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
//                        public void addItems(View v)
//                        listItems.add("Clicked : "+clickCounter++);
//                        adapter.notifyDataSetChanged();
                        gameLog.add(0, "...D-Reb");
                        arrayAdapter.notifyDataSetChanged();
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
//                        public void addItems(View v)
//                        listItems.add("Clicked : "+clickCounter++);
//                        adapter.notifyDataSetChanged();
                        gameLog.add(0, "...steal");
                        arrayAdapter.notifyDataSetChanged();
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
//                        public void addItems(View v)
//                        listItems.add("Clicked : "+clickCounter++);
//                        adapter.notifyDataSetChanged();
                        gameLog.add(0, "...assist");
                        arrayAdapter.notifyDataSetChanged();
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
//                        public void addItems(View v)
//                        listItems.add("Clicked : "+clickCounter++);
//                        adapter.notifyDataSetChanged();
                        gameLog.add(0, "...turnover");
                        arrayAdapter.notifyDataSetChanged();
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
//                        public void addItems(View v)
//                        listItems.add("Clicked : "+clickCounter++);
//                        adapter.notifyDataSetChanged();
                        gameLog.add(0, "...block");
                        arrayAdapter.notifyDataSetChanged();
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
//                        public void addItems(View v)
//                        listItems.add("Clicked : "+clickCounter++);
//                        adapter.notifyDataSetChanged();
                        gameLog.add(0, "...sub");
                        arrayAdapter.notifyDataSetChanged();
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
                bundle.putString("team_id", "");
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

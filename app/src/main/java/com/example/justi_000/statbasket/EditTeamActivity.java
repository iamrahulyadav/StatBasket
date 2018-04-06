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


public class EditTeamActivity extends AppCompatActivity implements View.OnClickListener
{
    DbHelper myDatabase;
    Bundle bundle = new Bundle();
    ListView lvPlayerList;
    List<Player> players;
    List<String> player_names;
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
            players = myDatabase.getAllPlayers(team.getId());
        }
        player_names = new ArrayList<>();
        for(Player player : players)
        {
            player_names.add(player.getFirstName() + " " + player.getLastName());
        }

        lvPlayerList = findViewById(R.id.lv_item_list);
        lvPlayerList.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, player_names));

        lvPlayerList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent i = new Intent(EditTeamActivity.this, ViewPlayerActivity.class);
                bundle.putLong("player_id", players.get(position).getId());
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
                Intent i = new Intent(EditTeamActivity.this, ViewPlayerActivity.class);
                bundle.putLong("team_id", team.getId());
                i.putExtras(bundle);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
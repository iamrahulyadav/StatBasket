package com.example.justi_000.statbasket;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DbHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DBHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "StatBasket";

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //TABLE NAMES

    private static final String TABLE_TEAM = "team";
    private static final String TABLE_PLAYER = "player";
    private static final String TABLE_PLAYER_TEAM = "player_team";

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //TABLE COLUMN NAMES

    // COMMON Columns
    private static final String COLUMN_CREATED_DATE = "created_date";

    // TEAM Table - column names
    //COLUMN_TEAM_ID
    private static final String COLUMN_TEAM_NAME = "team_name";

    // PLAYER Table - column names
    //COLUMN_PLAYER_ID
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_NUMBER = "number";
    private static final String COLUMN_HEIGHT_FEET = "height_feet";
    private static final String COLUMN_HEIGHT_INCHES = "height_inches";

    // PLAYER_TEAM Table - column names - also shared with TEAM and PLAYER tables
    //private static final String COLUMN_PLAYER_TEAM_ID = "player_team_id";
    private static final String COLUMN_TEAM_ID = "team_id";
    private static final String COLUMN_PLAYER_ID = "player_id";

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //CREATE TABLE STATEMENTS

    // TEAM table create statement
    private static final String CREATE_TABLE_TEAM = "CREATE TABLE "
            + TABLE_TEAM + "(" + COLUMN_TEAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TEAM_NAME
            + " TEXT," + COLUMN_CREATED_DATE + " DATETIME)";

    // PLAYER table create statement
    private static final String CREATE_TABLE_PLAYER = "CREATE TABLE " + TABLE_PLAYER
            + "(" + COLUMN_PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_FIRST_NAME + " TEXT,"
            + COLUMN_LAST_NAME + " TEXT," + COLUMN_NUMBER + " INTEGER,"
            + COLUMN_HEIGHT_FEET + " INTEGER," + COLUMN_HEIGHT_INCHES + " INTEGER,"
            + COLUMN_CREATED_DATE + " DATETIME" + ")";

    // PLAYER_TEAM table create statement
    private static final String CREATE_TABLE_PLAYER_TEAM = "CREATE TABLE "
            + TABLE_PLAYER_TEAM + "(" +  COLUMN_TEAM_ID + " INTEGER," + COLUMN_PLAYER_ID + " INTEGER,"
            + COLUMN_CREATED_DATE + " DATETIME, " + "PRIMARY KEY (" + COLUMN_TEAM_ID + ", " + COLUMN_PLAYER_ID + "))";


//    private static final String CREATE_TABLE_PLAYER_TEAM = "CREATE TABLE "
//            + TABLE_PLAYER_TEAM + "(" + COLUMN_PLAYER_TEAM_ID + " INTEGER PRIMARY KEY,"
//            + COLUMN_TEAM_ID + " INTEGER," + COLUMN_PLAYER_ID + " INTEGER,"
//            + COLUMN_CREATED_DATE + " DATETIME" + ")";

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //DbHelper METHODS

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_PLAYER);
        db.execSQL(CREATE_TABLE_TEAM);
        db.execSQL(CREATE_TABLE_PLAYER_TEAM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER_TEAM);

        // create new tables
        onCreate(db);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //PLAYER METHODS

    public long createPlayer(Player player, long team_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, player.getFirstName());
        values.put(COLUMN_LAST_NAME, player.getLastName());
        values.put(COLUMN_NUMBER, player.getNumber());
        values.put(COLUMN_HEIGHT_FEET, player.getHeightFeet());
        values.put(COLUMN_HEIGHT_INCHES, player.getHeightInches());
        values.put(COLUMN_CREATED_DATE, getCurrentDateTime());

        // insert row
        long player_id = db.insert(TABLE_PLAYER, null, values);

        // assign a team to player
        createPlayerTeam(player_id, team_id);

        return player_id;
    }

//    public Player getPlayer(long player_id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String query = "SELECT  * FROM " + TABLE_PLAYER + " WHERE "
//                + COLUMN_PLAYER_ID + " = " + player_id;
//
//        Log.e(LOG, query);
//
//        Cursor cursor = db.rawQuery(query, null);
//
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        Player player = new Player();
//        player.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_PLAYER_ID)));
//        player.setFirstName((cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME))));
//        player.setLastName((cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME))));
//        player.setNumber((cursor.getInt(cursor.getColumnIndex(COLUMN_NUMBER))));
//        player.setHeightFeet((cursor.getInt(cursor.getColumnIndex(COLUMN_HEIGHT_FEET))));
//        player.setHeightInches((cursor.getInt(cursor.getColumnIndex(COLUMN_HEIGHT_INCHES))));
//        player.setCreatedDate(cursor.getString(cursor.getColumnIndex(COLUMN_CREATED_DATE)));
//
//        return player;
//    }

    public Player getPlayer(String player_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_PLAYER + " WHERE "
                + COLUMN_PLAYER_ID + " = " + player_id;

        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null)
            cursor.moveToFirst();

        Player player = new Player();
        player.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_PLAYER_ID)));
        player.setFirstName((cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME))));
        player.setLastName((cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME))));
        player.setNumber((cursor.getInt(cursor.getColumnIndex(COLUMN_NUMBER))));
        player.setHeightFeet((cursor.getInt(cursor.getColumnIndex(COLUMN_HEIGHT_FEET))));
        player.setHeightInches((cursor.getInt(cursor.getColumnIndex(COLUMN_HEIGHT_INCHES))));
        player.setCreatedDate(cursor.getString(cursor.getColumnIndex(COLUMN_CREATED_DATE)));

        return player;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //TEAM METHODS

    public long createTeam(Team team) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TEAM_NAME, team.getName());
        values.put(COLUMN_CREATED_DATE, getCurrentDateTime());

        // insert row
        long team_id = db.insert(TABLE_TEAM, null, values);

        return team_id;
    }

//    public Team getTeam(long team_id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String query = "SELECT  * FROM " + TABLE_TEAM + " WHERE "
//                + COLUMN_TEAM_ID + " = " + team_id;
//
//        Log.e(LOG, query);
//
//        Cursor cursor = db.rawQuery(query, null);
//
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        Team team = new Team();
//        team.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_TEAM_ID)));
//        team.setName((cursor.getString(cursor.getColumnIndex(COLUMN_TEAM_NAME))));
//        team.setCreatedDate(cursor.getString(cursor.getColumnIndex(COLUMN_CREATED_DATE)));
//
//        return team;
//    }

    public Team getTeam(String team_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + TABLE_TEAM + " WHERE "
                + COLUMN_TEAM_ID + " = " + team_id;

        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null)
            cursor.moveToFirst();

        Team team = new Team();
        team.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_TEAM_ID)));
        team.setName((cursor.getString(cursor.getColumnIndex(COLUMN_TEAM_NAME))));
        team.setCreatedDate(cursor.getString(cursor.getColumnIndex(COLUMN_CREATED_DATE)));

        return team;
    }

    public List<Team> getAllTeams() {
        List<Team> teams = new ArrayList<Team>();
        String query = "SELECT  * FROM " + TABLE_TEAM;

        Log.e(LOG, query);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        //add all rows from database to list of teams
        if (cursor.moveToFirst()) {
            do {
                Team team = new Team();
                team.setId(cursor.getInt((cursor.getColumnIndex(COLUMN_TEAM_ID))));
                team.setName((cursor.getString(cursor.getColumnIndex(COLUMN_TEAM_NAME))));
                team.setCreatedDate(cursor.getString(cursor.getColumnIndex(COLUMN_CREATED_DATE)));
                teams.add(team);
            } while (cursor.moveToNext());
        }

        return teams;
    }

    public int updateTeam(Team team) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TEAM_NAME, team.getName());

        // updating row
        return db.update(TABLE_TEAM, values, COLUMN_TEAM_ID + " = ?",
                new String[] { String.valueOf(team.getId()) });
    }


    public int deleteTeam(long team_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TEAM, COLUMN_TEAM_ID + " = ?",
                new String[] { String.valueOf(team_id) });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //PLAYER_TEAM METHODS

    public long createPlayerTeam(long player_id, long team_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PLAYER_ID, player_id);
        values.put(COLUMN_TEAM_ID, team_id);
        values.put(COLUMN_CREATED_DATE, getCurrentDateTime());

        long player_team_id = db.insert(TABLE_PLAYER_TEAM, null, values);

        return player_team_id;
    }

    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
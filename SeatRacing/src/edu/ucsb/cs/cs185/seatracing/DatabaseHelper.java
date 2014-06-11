package edu.ucsb.cs.cs185.seatracing;

import edu.ucsb.cs.cs185.seatracing.model.Boat;
import edu.ucsb.cs.cs185.seatracing.model.Result;
import edu.ucsb.cs.cs185.seatracing.model.Round;
import edu.ucsb.cs.cs185.seatracing.model.Rower;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
	
	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "seatRaceManager";
 
    // Rowers table name
    private static final String TABLE_ROWERS = "rowers";
 
    // Rowers Table Columns names
    private static final String KEY_ROWERS_ID = "id";
    private static final String KEY_ROWERS_NAME = "name";
    
    // Boats table name
    private static final String TABLE_BOATS = "boats";
 
    // Boats Table Columns names
    private static final String KEY_BOATS_ID = "id";
    private static final String KEY_BOATS_NAME = "name";
    private static final String KEY_BOATS_SIZE = "size";
    
    // Results table name
    private static final String TABLE_RESULTS = "results";
 
    // Results Table Columns names
    private static final String KEY_RESULTS_ID = "id";
    private static final String KEY_RESULTS_ROUNDID = "roundid";
    private static final String KEY_RESULTS_ROWERID = "rowerid";
    private static final String KEY_RESULTS_BOATID = "boatid";
    private static final String KEY_RESULTS_TIME = "time";
    private static final String KEY_RESULTS_DATE = "date";
    
    // Rounds table name
    private static final String TABLE_ROUNDS = "rounds";
 
    // Rounds Table Columns names
    private static final String KEY_ROUNDS_ID = "id";
    private static final String KEY_ROUNDS_DATE = "date";
    private static final String KEY_ROUNDS_SIZE = "size";
 
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_ROWERS_TABLE = "CREATE TABLE " + TABLE_ROWERS + "("
                + KEY_ROWERS_ID + " INTEGER PRIMARY KEY," + KEY_ROWERS_NAME + " TEXT" + ")";
        db.execSQL(CREATE_ROWERS_TABLE);
        String CREATE_BOATS_TABLE = "CREATE TABLE " + TABLE_BOATS + "("
                + KEY_BOATS_ID + " INTEGER PRIMARY KEY," + KEY_BOATS_NAME + " TEXT,"
                + KEY_BOATS_SIZE + " INTEGER" + ")";
        db.execSQL(CREATE_BOATS_TABLE);
        String CREATE_RESULTS_TABLE = "CREATE TABLE " + TABLE_RESULTS + "("
                + KEY_RESULTS_ID + " INTEGER PRIMARY KEY," + KEY_RESULTS_ROUNDID + " INTEGER,"
                + KEY_RESULTS_ROWERID + " INTEGER," + KEY_RESULTS_BOATID + " INTEGER,"
                + KEY_RESULTS_TIME + " INTEGER," + KEY_RESULTS_DATE + " INTEGER" + ")";
        db.execSQL(CREATE_RESULTS_TABLE);
        String CREATE_ROUNDS_TABLE = "CREATE TABLE " + TABLE_ROUNDS + "("
                + KEY_ROUNDS_ID + " INTEGER PRIMARY KEY," + KEY_ROUNDS_DATE + " INTEGER,"
                + KEY_ROUNDS_SIZE + " INTEGER" + ")";
        db.execSQL(CREATE_ROUNDS_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROWERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOATS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUNDS);
 
        // Create tables again
        onCreate(db);
		
	}
	
	///CRUD stuff
	
	void addRower(Rower rower) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_ROWERS_NAME, rower.name()); // Rower Name
 
        // Inserting Row
        db.insert(TABLE_ROWERS, null, values);
        db.close(); // Closing database connection
    }
	
	void addBoat(Boat boat) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_BOATS_NAME, boat.name()); // Boat Name
        values.put(KEY_BOATS_SIZE, boat.size()); // Boat Size
 
        // Inserting Row
        db.insert(TABLE_BOATS, null, values);
        db.close(); // Closing database connection
    }
	
	void addResult(Result result) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_RESULTS_ROUNDID, result.round()); // Result Round ID
        values.put(KEY_RESULTS_ROWERID, result.rower()); // Result Rower ID
        values.put(KEY_RESULTS_BOATID, result.boat()); // Result Boat ID
        values.put(KEY_RESULTS_TIME, result.time()); // Result Time
        values.put(KEY_RESULTS_DATE, result.date()); // Result Date
 
        // Inserting Row
        db.insert(TABLE_RESULTS, null, values);
        db.close(); // Closing database connection
    }
	
	void addRound(Round round) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_ROUNDS_DATE, round.dateCreated()); // Round Date
        //TODO: fix this
        //values.put(KEY_ROUNDS_SIZE, round.size()); // Round Size
 
        // Inserting Row
        db.insert(TABLE_ROUNDS, null, values);
        db.close(); // Closing database connection
    }

}

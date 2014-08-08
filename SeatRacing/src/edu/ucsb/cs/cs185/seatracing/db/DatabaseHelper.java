package edu.ucsb.cs.cs185.seatracing.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import edu.ucsb.cs.cs185.seatracing.model.Boat;
import edu.ucsb.cs.cs185.seatracing.model.RacingSet;
import edu.ucsb.cs.cs185.seatracing.model.Result;
import edu.ucsb.cs.cs185.seatracing.model.Round;
import edu.ucsb.cs.cs185.seatracing.model.Rower;

public class DatabaseHelper extends SQLiteOpenHelper{

	private static final String TAG = "SeatRaceDBHelper";
	
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 8;

	// Database Name
	private static final String DATABASE_NAME = "seatRaceManager";

	// Rowers table name
	private static final String TABLE_ROWERS = "rowers";

	// Rowers Table Columns names
	private static final String KEY_ROWERS_ID = "rower_id";
	private static final String KEY_ROWERS_NAME = "name";

	// Boats table name
	private static final String TABLE_BOATS = "boats";

	// Boats Table Columns names
	private static final String KEY_BOATS_ID = "boat_id";
	private static final String KEY_BOATS_NAME = "name";
	private static final String KEY_BOATS_SIZE = "max_size";

	// Results table name
	private static final String TABLE_RESULTS = "results";

	// Results Table Columns names
	private static final String KEY_RESULTS_ID = "result_id";
	private static final String KEY_RESULTS_ROUNDID = "round_id";
	private static final String KEY_RESULTS_ROWERID = "rower_id";
	private static final String KEY_RESULTS_BOATID = "boat_id";
	private static final String KEY_RESULTS_RACE = "race_index";
	private static final String KEY_RESULTS_TIME = "res_time";
	private static final String KEY_RESULTS_DATE = "date";
	private static final String KEY_RESULTS_SEAT = "seat";

	// Rounds table name
	private static final String TABLE_ROUNDS = "rounds";

	// Rounds Table Columns names
	private static final String KEY_ROUNDS_ID = "round_id";
	private static final String KEY_ROUNDS_DATE = "date";
	private static final String KEY_ROUNDS_SIZE = "num_races";

	// Lineups table name
	private static final String TABLE_LINEUPS = "lineups";

	// Lineup Table Columns names
	private static final String KEY_LINEUPS_ID = "lineup_id";
	private static final String KEY_LINEUPS_ROUNDID = "round_id";
	private static final String KEY_LINEUPS_ROWERID = "rower_id";
	private static final String KEY_LINEUPS_BOATID = "boat_id";
	private static final String KEY_LINEUPS_SEAT = "seat";
	private static final String KEY_LINEUPS_SET = "set_index";

	//Singleton
	private static DatabaseHelper instance;

	public static synchronized DatabaseHelper getInstance(Context context){
		if(instance==null){
			instance = new DatabaseHelper(context);
		}
		return instance;
	}

	private DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_ROWERS_TABLE = "CREATE TABLE " + TABLE_ROWERS + "("
				+ KEY_ROWERS_ID + " INTEGER PRIMARY KEY," + KEY_ROWERS_NAME + " TEXT NOT NULL UNIQUE " + ")";
		db.execSQL(CREATE_ROWERS_TABLE);
		String CREATE_BOATS_TABLE = "CREATE TABLE " + TABLE_BOATS + "("
				+ KEY_BOATS_ID + " INTEGER PRIMARY KEY," + KEY_BOATS_NAME + " TEXT UNIQUE NOT NULL,"
				+ KEY_BOATS_SIZE + " INTEGER" + ")";
		db.execSQL(CREATE_BOATS_TABLE);
		String CREATE_RESULTS_TABLE = "CREATE TABLE " + TABLE_RESULTS + "("
				+ KEY_RESULTS_ID + " INTEGER PRIMARY KEY," + KEY_RESULTS_ROUNDID + " INTEGER NOT NULL,"
				+ KEY_RESULTS_ROWERID + " INTEGER NOT NULL," + KEY_RESULTS_BOATID + " INTEGER NOT NULL,"
				+ KEY_RESULTS_RACE + " INTEGER," + KEY_RESULTS_TIME + " INTEGER NOT NULL,"
				+ KEY_RESULTS_DATE + " INTEGER," + KEY_RESULTS_SEAT + " INTEGER NOT NULL,"
				+ "FOREIGN KEY(" + KEY_RESULTS_ROWERID + ") REFERENCES " + TABLE_ROWERS + "(" + KEY_ROWERS_ID + "),"
				+ "FOREIGN KEY(" + KEY_RESULTS_BOATID + ") REFERENCES " + TABLE_BOATS + "(" + KEY_BOATS_ID + "),"
				+ "FOREIGN KEY(" + KEY_RESULTS_ROUNDID + ") REFERENCES " + TABLE_RESULTS + "(" + KEY_RESULTS_ID + ") )";
		db.execSQL(CREATE_RESULTS_TABLE);
		String CREATE_ROUNDS_TABLE = "CREATE TABLE " + TABLE_ROUNDS + "("
				+ KEY_ROUNDS_ID + " INTEGER PRIMARY KEY," + KEY_ROUNDS_DATE + " INTEGER,"
				+ KEY_ROUNDS_SIZE + " INTEGER DEFAULT 0" + ")";
		db.execSQL(CREATE_ROUNDS_TABLE);
		String CREATE_LINEUPS_TABLE = "CREATE TABLE " + TABLE_LINEUPS + "("
				+ KEY_LINEUPS_ID + " INTEGER PRIMARY KEY," + KEY_LINEUPS_ROUNDID + " INTEGER NOT NULL,"
				+ KEY_LINEUPS_ROWERID + " INTEGER NOT NULL," + KEY_LINEUPS_BOATID + " INTEGER NOT NULL,"
				+ KEY_LINEUPS_SEAT + " INTEGER NOT NULL," + KEY_LINEUPS_SET + " INTEGER NOT NULL,"
				+ "FOREIGN KEY(" + KEY_LINEUPS_ROUNDID + ") REFERENCES " + TABLE_ROUNDS + "(" + KEY_ROUNDS_ID + "),"
				+ "FOREIGN KEY(" + KEY_LINEUPS_ROWERID + ") REFERENCES " + TABLE_ROWERS + "(" + KEY_ROWERS_ID + "),"
				+ "FOREIGN KEY(" + KEY_LINEUPS_BOATID + ") REFERENCES " + TABLE_BOATS + "(" + KEY_BOATS_ID + ") )";
		db.execSQL(CREATE_LINEUPS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROWERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOATS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUNDS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LINEUPS);
		
		Log.i(TAG, "upgrading DB: "+oldVersion+" to "+newVersion);

		// Create tables again
		onCreate(db);
	}

	///CRUD stuff

	/**
	 * This adds the provided rower to the database, or if already present 
	 * simply updates the ID in the model object.
	 * @param rower Rower to add or update
	 */
	public int addRower(Rower rower) {
		SQLiteDatabase db = this.getWritableDatabase();
		int id;

		Cursor cursor = db.query(TABLE_BOATS, new String[] { KEY_ROWERS_ID }, KEY_ROWERS_NAME + "=?",
				new String[] { rower.name() }, null, null, null, null);
		if (cursor != null && cursor.moveToFirst()){
			id = Integer.parseInt(cursor.getString(0));
		}else{
			ContentValues values = new ContentValues();
			values.put(KEY_ROWERS_NAME, rower.name()); // Rower Name

			// Inserting Row
			id = (int) db.insert(TABLE_ROWERS, null, values);
		}

		db.close();

		if (id!=-1){
			rower.setId(id);
		}

		return id;
	}
	
	public void batchAddRowers(Rower[] rowers){
		batchAddRowers(rowers, this.getWritableDatabase());
	}

	public void batchAddRowers(Rower[] rowers, SQLiteDatabase db){
		//SQLiteDatabase db = this.getWritableDatabase();

		try{
			db.beginTransaction();
			for(Rower rower : rowers){ 
				int id;
				Cursor cursor = db.query(TABLE_ROWERS, new String[] { KEY_ROWERS_ID }, KEY_ROWERS_NAME + "=?",
						new String[] { rower.name() }, null, null, null, null);
				if (cursor != null && cursor.moveToFirst()){
					id = Integer.parseInt(cursor.getString(0));
				}
				else{
					ContentValues values = new ContentValues();
					values.put(KEY_ROWERS_NAME, rower.name()); // Rower Name

					// Inserting Row
					id = (int) db.insertOrThrow(TABLE_ROWERS, null, values);
				}
				rower.setId(id);
			}
			db.setTransactionSuccessful();
		} catch (SQLException sqle){
			sqle.printStackTrace();
		} finally{
			db.endTransaction();
			db.close();
		}

	}
	
	/**
	 * This adds the provided boat to the database, or if already present
	 * simply updates the ID of the model object.
	 * @param boat Boat to add or update
	 */
	public int addBoat(Boat boat) {
		int id;
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.query(TABLE_BOATS, new String[] { KEY_BOATS_ID, KEY_BOATS_SIZE }, KEY_BOATS_NAME + "=?",
				new String[] { boat.name() }, null, null, null, null);
		if (cursor != null && cursor.moveToFirst()){
			id = Integer.parseInt(cursor.getString(0));
			if(Integer.parseInt(cursor.getString(1)) < boat.size()){
				//going to update max size
				ContentValues update = new ContentValues();
				update.put(KEY_BOATS_SIZE, boat.size());
				db.update(TABLE_BOATS, update, KEY_BOATS_ID+" = "+id, null);
			}
		}else{
			ContentValues values = new ContentValues();
			values.put(KEY_BOATS_NAME, boat.name()); // Boat Name
			values.put(KEY_BOATS_SIZE, boat.size()); // Boat Size

			// Inserting Row
			id = (int) db.insert(TABLE_BOATS, null, values);
		}
		db.close();

		if(id>0){
			boat.setID(id);
		}

		return id;
	}

	public int addResult(Result result) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_RESULTS_ROUNDID, result.round()); // Result Round ID
		values.put(KEY_RESULTS_ROWERID, result.rowerId()); // Result Rower ID
		values.put(KEY_RESULTS_BOATID, result.boatId()); // Result Boat ID
		values.put(KEY_RESULTS_RACE, result.raceNum()); // Result RaceNum
		values.put(KEY_RESULTS_TIME, result.time()); // Result Time
		values.put(KEY_RESULTS_DATE, result.date()); // Result Date

		// Inserting Row
		int id = (int)db.insert(TABLE_RESULTS, null, values);
		db.close(); // Closing database connection

		return id;
	}

	public void batchAddResults(List<Result> results){
		SQLiteDatabase db = this.getWritableDatabase();

		try{
			db.beginTransaction();
			for(Result result : results){
				ContentValues values = new ContentValues();
				values.put(KEY_RESULTS_ROUNDID, result.round()); // Result Round ID
				values.put(KEY_RESULTS_ROWERID, result.rowerId()); // Result Rower ID
				values.put(KEY_RESULTS_BOATID, result.boatId()); // Result Boat ID
				values.put(KEY_RESULTS_RACE, result.raceNum()); // Result RaceNum
				values.put(KEY_RESULTS_TIME, result.time()); // Result Time
				values.put(KEY_RESULTS_DATE, result.date()); // Result Date
				values.put(KEY_RESULTS_SEAT, result.seat()); // Result Seat

				// Inserting Row
				db.insertOrThrow(TABLE_RESULTS, null, values);
			}
			db.setTransactionSuccessful();
		} catch (SQLException sqle){
			sqle.printStackTrace();
		} finally{
			db.endTransaction();
			db.close();
		}
	}

	public int addRound(Round round) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ROUNDS_DATE, round.dateCreated()); // Round Date
		values.putNull(KEY_ROUNDS_SIZE);
		// Inserting Row
		int id = (int) db.insert(TABLE_ROUNDS, null, values);
		db.close(); // Closing database connection

		if(id>0){
			round.setID(id);
		}

		return id;
	}

	public void setRoundSize(Round round) {
		SQLiteDatabase db = this.getWritableDatabase();

		int size = round.getNumRaces();
		ContentValues values = new ContentValues();
		values.put(KEY_ROUNDS_SIZE, size);

		db.update(TABLE_ROUNDS, values, "round_id="+Integer.toString(round.getID()), null);

		db.close();
	}

	/**
	 * Adds lineup, rowers, and boats if necessary.
	 * Will not add duplicate rowers or boats if already present.
	 * Assumes round already added, and round id is valid.
	 * This must be called before the first switch to be valid!
	 * @param rs racingset to add
	 * @param roundID id of round to add lineups to
	 */
	public void addLineups(int roundID, int setID, RacingSet rs) {
		SQLiteDatabase db = this.getWritableDatabase();

		try{
			db.beginTransaction();

			//maybe add boats and rowers from here? should be safe from dups, just slower
			//addBoat(rs.getBoat1());
			//addBoat(rs.getBoat2());

			for(int i=0; i<rs.getBoats().length; ++i){
				for(int j=0; j<rs.getBoat1().getRowers().length; ++j){
					//Rower r = rs.getBoats()[i].getRower(j);
					//addRower(r);
					//add to lineup
					ContentValues values = new ContentValues();
					values.put(KEY_LINEUPS_ROUNDID, roundID);
					values.put(KEY_LINEUPS_ROWERID, rs.getBoats()[i].getRower(j).id());
					values.put(KEY_LINEUPS_BOATID, rs.getBoats()[i].getID());
					values.put(KEY_LINEUPS_SEAT, j);
					values.put(KEY_LINEUPS_SET, setID);
					db.insert(TABLE_LINEUPS, null, values);
				}
			}

			db.setTransactionSuccessful();
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
		}
		finally{
			db.endTransaction();
			db.close();
		}

	}

	// Getting list of Results
	public List<Result> getResults(int roundID) {
		List<Result> resultsList = new ArrayList<Result>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_RESULTS
				+ " WHERE " + KEY_RESULTS_ID + " = " + Integer.toString(roundID) ;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				int round = Integer.parseInt(cursor.getString(0));
				int rower = Integer.parseInt(cursor.getString(1));
				int boat = Integer.parseInt(cursor.getString(2));
				int race = Integer.parseInt(cursor.getString(3));
				long time = Long.parseLong(cursor.getString(4));
				long date = Long.parseLong(cursor.getString(5));
				int seat = Integer.parseInt(cursor.getString(6));
				Result result = new Result(round,rower,boat, seat, race, time,date);
				// Adding result to list
				resultsList.add(result);
			} while (cursor.moveToNext());
		}

		// return contact list
		return resultsList;
	}

	public List<String> getBoatNames(){
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_BOATS, new String[]{ KEY_BOATS_NAME }, null, null, null, null, null);

		List<String> ret = new ArrayList<String>(cursor.getCount());

		if(cursor.moveToFirst()){
			do{
				String boatName = cursor.getString(0);
				ret.add(boatName);
			}while(cursor.moveToNext());
		}
		return ret;
	}

	public List<String> getRowerNames(){
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_ROWERS, new String[]{ KEY_ROWERS_NAME }, null, null, null, null, null);

		List<String> ret = new ArrayList<String>(cursor.getCount());

		if(cursor.moveToFirst()){
			do{
				String rowerName = cursor.getString(0);
				ret.add(rowerName);
			}while(cursor.moveToNext());
		}
		return ret;
	}
	
	public Cursor getHistoricResultsCursor(){
		SQLiteDatabase db = this.getReadableDatabase();
				
		Cursor cursor = db.rawQuery("SELECT rn."+KEY_ROUNDS_ID+" AS _id, rn."+KEY_ROUNDS_DATE
				+", rn."+KEY_ROUNDS_SIZE+", COUNT(DISTINCT rs."+KEY_RESULTS_RACE
				+") AS num_race_results, COUNT(DISTINCT ln."+KEY_LINEUPS_BOATID
				+") AS num_boats, COUNT(DISTINCT ln."+KEY_LINEUPS_ROWERID+") AS total_rowers FROM "
				+TABLE_ROUNDS+" as rn LEFT JOIN "+TABLE_RESULTS+" AS rs ON (rs."+KEY_RESULTS_ROUNDID+" = rn."
				+KEY_ROUNDS_ID+") LEFT JOIN "+TABLE_LINEUPS+" AS ln ON (ln."+KEY_LINEUPS_ROUNDID+" = rn."+KEY_ROUNDS_ID
				+") GROUP BY rn."+KEY_ROUNDS_ID, null);
		
		return cursor;
	}
	
}

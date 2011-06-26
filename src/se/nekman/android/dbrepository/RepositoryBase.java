package se.nekman.android.dbrepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * Helper class for Android SQLiteDatabase
 * @param <T>
 */
public class RepositoryBase<T> {
	
	private static final String LOG_TAG = "RepositoryBase";
	
	private final IEntityMapper<T> mapper;
	protected final SQLiteDatabase db;
	
	/**
	 * 
	 * @param db
	 * @param mapper
	 */
	protected RepositoryBase(final SQLiteDatabase db, final IEntityMapper<T> mapper) {
		this.mapper = mapper;
		this.db = db;
	}
	
	/**
	 * 
	 * @param table
	 * @param nullColumnHack
	 * @param values
	 * @return
	 */
	protected long insert(final String table, final String nullColumnHack, final ContentValues values) {		
		return db.insert(table, nullColumnHack, values);
	}
	
	/**
	 * 
	 * @param table
	 * @param values
	 * @return
	 */
	protected long insert(final String table, final ContentValues values) {		
		return insert(table, null, values);
	}
	
	/**
	 * 
	 * @param table
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	protected long update(final String table, final ContentValues values, final String whereClause, final String[] whereArgs) {
		return db.update(table, values, whereClause, whereArgs);
	}

	/**
	 * 
	 * @param table
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	protected long delete(final String table, final String whereClause, final String[] whereArgs) {	
		return db.delete(table, whereClause, whereArgs);
	}
	
	/**
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	protected List<T> queryForList(final String sql, final String[] args) {	
		Cursor c = null;
		try { 
			// query for result
			c = db.rawQuery(sql, args);
			final List<T> rows = new ArrayList<T>();
			// map result to entities and return
			while (c.moveToNext()) {
				rows.add(mapper.toEntity(c));
			}
			
			return rows;
			
		} catch (final SQLiteException e) {
			Log.e(LOG_TAG, "Error when running SQL-query: " + sql, e);
		} finally {
			close(c);
		}
		
		return Collections.emptyList();
	}

	/**
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	protected T queryForObject(final String sql, final String[] args) {
		Cursor c = null;
		try { 
			// query for result
			c = db.rawQuery(sql, args);
			// map and return entity 
			if (c.moveToNext()) {
				return mapper.toEntity(c);
			}
			
		} catch (final SQLiteException e) {
			Log.e(LOG_TAG, "Error when running SQL-query: " + sql, e);
		}  finally {
			close(c);
		}
		
		return null;
	}

	private static void close(final Cursor c) {
		if (c == null || c.isClosed()) {
			return;
		}
		
		c.close();
	}
}

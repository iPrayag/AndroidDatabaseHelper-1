package se.nekman.android.dbrepository;

import android.database.Cursor;

public interface IEntityMapper<T> {
	/**
	 * 
	 * @param cursor
	 * @return
	 */
	T toEntity(Cursor cursor);
}

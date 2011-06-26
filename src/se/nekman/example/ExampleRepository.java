package se.nekman.example;

import java.util.List;

import se.nekman.android.dbrepository.IEntityMapper;
import se.nekman.android.dbrepository.RepositoryBase;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ExampleRepository extends RepositoryBase<MyEntity> {

	public ExampleRepository(final SQLiteDatabase db) {
		super(db, Mapper.INSTANCE);
	}
	
	public List<MyEntity> getAll() {
		return queryForList("select * from mytable", null);
	}
	
	public MyEntity getById(String id) {
		return queryForObject("select * from mytable where id = ?", new String [] { id });
	}
		
	private static final class Mapper implements IEntityMapper<MyEntity> {

		public static final IEntityMapper<MyEntity> INSTANCE = new Mapper();
		
		public MyEntity toEntity(final Cursor c) {
			return new MyEntity(); //TODO:
		}
	}
}

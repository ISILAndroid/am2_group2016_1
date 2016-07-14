package com.isil.am2.template.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.isil.am2.template.model.FacultyEntity;

import java.util.ArrayList;
import java.util.List;

public class CRUDOperations {

	private MyDatabase helper;
	public CRUDOperations(SQLiteOpenHelper _helper) {
		super();
		// TODO Auto-generated constructor stub
		helper =(MyDatabase)_helper;
	}

	public void addFaculty(FacultyEntity facultyEntity)
	{
		 SQLiteDatabase db = helper.getWritableDatabase(); //modo escritura
		 ContentValues values = new ContentValues();
		 values.put(MyDatabase.KEY_NAME, facultyEntity.getName());
		 values.put(MyDatabase.KEY_PHOTO, facultyEntity.getPhoto());
		 db.insert(MyDatabase.TABLE_NOTES, null, values);
		 db.close();
	}
	
	public FacultyEntity getFaculty(int id)
	{
		SQLiteDatabase db = helper.getReadableDatabase(); //modo lectura
		Cursor cursor = db.query(MyDatabase.TABLE_NOTES,
				new String[]{MyDatabase.KEY_ID, MyDatabase.KEY_NAME,
						MyDatabase.KEY_PHOTO},
				MyDatabase.KEY_ID + "=?",
				new String[]{String.valueOf(id)}, null, null, null);
		if(cursor!=null)
		{
			cursor.moveToFirst();
		}
		int nid = Integer.parseInt(cursor.getString(0));
		String name = cursor.getString(1);
		int photo = cursor.getInt(2);

		FacultyEntity facultyEntity= new FacultyEntity();
			facultyEntity.setId(nid);
		facultyEntity.setName(name);
		facultyEntity.setPhoto(photo);
		return facultyEntity;
	}
	
	public List<FacultyEntity> getAllFaculties()
	{
		List<FacultyEntity> lst =new ArrayList<FacultyEntity>();
		String sql= "SELECT  * FROM " + MyDatabase.TABLE_NOTES;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor.moveToFirst())
		{
			do
			{
				FacultyEntity noteEntity =new FacultyEntity();
				noteEntity.setId(Integer.parseInt(cursor.getString(0)));
				noteEntity.setName(cursor.getString(1));
				noteEntity.setPhoto(cursor.getInt(2));
				lst.add(noteEntity);
			}while(cursor.moveToNext());
		}
		return lst;
	}
}

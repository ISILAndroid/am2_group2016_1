## Base de datos Sqlite (S4-S6)

  - Base de datos Sqlite
  - Ejemplo CRUD
  
### Aplicación MyNotes

![splash](https://github.com/ISILAndroid/am2_group2016_1/blob/Lesson4/screenshots/Splash.png)

#### Clases para manejar la bd de Datos local.
 - MyDatabase.java [com.isil.mynotes.storage.db.MyDatabase] 
 
 ```
package com.isil.mynotes.storage.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper {


	public static final int DATABASE_VERSION = 1;
 
	public static final String DATABASE_NAME = "BDNote";
 
    public static final String TABLE_NOTES = "tb_notes";
    
    //Columnas de la Tabla Contacts
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESC = "desc";
    public static final String KEY_PATH = "path";
    
    
    public MyDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
    
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql= "CREATE TABLE " + TABLE_NOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + KEY_NAME + " TEXT,"
				+ KEY_DESC + " TEXT,"
                + KEY_PATH + " TEXT" + ")";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql= "DROP TABLE IF EXISTS " + TABLE_NOTES;
		db.execSQL(sql);
	}

}
 ```
- CRUDOperations.java [com.isil.mynotes.storage.db.CRUDOperations] 
 ```
package com.isil.mynotes.storage.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.isil.mynotes.model.entity.NoteEntity;

import java.util.ArrayList;
import java.util.List;

public class CRUDOperations {

	private MyDatabase helper;
	public CRUDOperations(SQLiteOpenHelper _helper) {
		super();
		// TODO Auto-generated constructor stub
		helper =(MyDatabase)_helper;
	}

	public void addNote(NoteEntity noteEntity)
	{
		 SQLiteDatabase db = helper.getWritableDatabase(); //modo escritura
		 ContentValues values = new ContentValues();
		 values.put(MyDatabase.KEY_NAME, noteEntity.getName());
		 values.put(MyDatabase.KEY_DESC, noteEntity.getDescription());
		 values.put(MyDatabase.KEY_PATH, noteEntity.getPath());
		 
		 db.insert(MyDatabase.TABLE_NOTES, null, values);
		 db.close();
	}
	
	public NoteEntity getNote(int id)
	{
		SQLiteDatabase db = helper.getReadableDatabase(); //modo lectura
		Cursor cursor = db.query(MyDatabase.TABLE_NOTES,
				new String[]{MyDatabase.KEY_ID, MyDatabase.KEY_NAME,
						MyDatabase.KEY_DESC, MyDatabase.KEY_PATH},
				MyDatabase.KEY_ID + "=?",
				new String[]{String.valueOf(id)}, null, null, null);
		if(cursor!=null)
		{
			cursor.moveToFirst();
		}
		int nid = Integer.parseInt(cursor.getString(0));
		String name = cursor.getString(1);
		String desc = cursor.getString(2);
		String path = cursor.getString(3);

		NoteEntity noteEntity= new NoteEntity(
				nid, name, desc,path);
		return noteEntity;
	}
	
	public List<NoteEntity> getAllNotes()
	{
		List<NoteEntity> lst =new ArrayList<NoteEntity>();
		String sql= "SELECT  * FROM " + MyDatabase.TABLE_NOTES;
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor.moveToFirst())
		{
			do
			{
				NoteEntity contact =new NoteEntity();
				contact.setId(Integer.parseInt(cursor.getString(0)));
				contact.setName(cursor.getString(1));
				contact.setDescription(cursor.getString(2));
				contact.setPath(cursor.getString(3));

				lst.add(contact);
			}while(cursor.moveToNext());
		}
		return lst;
	}
	
	public int getNotesCount()
	{
		String sql= "SELECT * FROM "+MyDatabase.TABLE_NOTES;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		cursor.close();
		
		return cursor.getCount();
	}
	
	//--------------------------------------------
	
	public int updateNote(NoteEntity noteEntity)
	{
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MyDatabase.KEY_NAME, noteEntity.getName());
		values.put(MyDatabase.KEY_DESC, noteEntity.getDescription());
		values.put(MyDatabase.KEY_PATH, noteEntity.getPath());

		return db.update(MyDatabase.TABLE_NOTES,
				values,
				MyDatabase.KEY_ID+"=?",
				new String[]{String.valueOf(noteEntity.getId())});
	}
	//--------------------------------------------
	
	public int deleteNote(NoteEntity noteEntity)
	{
		 SQLiteDatabase db = helper.getWritableDatabase(); 
		 int row= db.delete(MyDatabase.TABLE_NOTES,
				 MyDatabase.KEY_ID+"=?", 
				 new String[]{String.valueOf(noteEntity.getId())});
		 db.close();
		return row;
	}
}

 ```
 1. Agregar nota
  ```
	public void addNote(NoteEntity noteEntity)
	{
		 SQLiteDatabase db = helper.getWritableDatabase(); //modo escritura
		 ContentValues values = new ContentValues();
		 values.put(MyDatabase.KEY_NAME, noteEntity.getName());
		 values.put(MyDatabase.KEY_DESC, noteEntity.getDescription());
		 values.put(MyDatabase.KEY_PATH, noteEntity.getPath());
		 
		 db.insert(MyDatabase.TABLE_NOTES, null, values);
		 db.close();
	}
  ```
 2. Devolver un nota por ID
  ```
	  public NoteEntity getNote(int id)
	  {
		SQLiteDatabase db = helper.getReadableDatabase(); //modo lectura
		Cursor cursor = db.query(MyDatabase.TABLE_NOTES,
				new String[]{MyDatabase.KEY_ID, MyDatabase.KEY_NAME,
						MyDatabase.KEY_DESC, MyDatabase.KEY_PATH},
				MyDatabase.KEY_ID + "=?",
				new String[]{String.valueOf(id)}, null, null, null);
		if(cursor!=null)
		{
			cursor.moveToFirst();
		}
		int nid = Integer.parseInt(cursor.getString(0));
		String name = cursor.getString(1);
		String desc = cursor.getString(2);
		String path = cursor.getString(3);
	
		NoteEntity noteEntity= new NoteEntity(
				nid, name, desc,path);
		return noteEntity;
	  }
  ```
 3. Listar todas las notas
 ```
 	public List<NoteEntity> getAllNotes()
	{
	List<NoteEntity> lst =new ArrayList<NoteEntity>();
	String sql= "SELECT  * FROM " + MyDatabase.TABLE_NOTES;
	SQLiteDatabase db = helper.getWritableDatabase();
	Cursor cursor = db.rawQuery(sql, null);
	if(cursor.moveToFirst())
	{
		do
		{
			NoteEntity contact =new NoteEntity();
			contact.setId(Integer.parseInt(cursor.getString(0)));
			contact.setName(cursor.getString(1));
			contact.setDescription(cursor.getString(2));
			contact.setPath(cursor.getString(3));

			lst.add(contact);
		}while(cursor.moveToNext());
	}
	return lst;
}
 ```
 4. Total de notas 
  ```
	public int getNotesCount()
	{
		String sql= "SELECT * FROM "+MyDatabase.TABLE_NOTES;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		cursor.close();
		
		return cursor.getCount();
	}
  ```
 5. Actualizar una nota
  ```
	public int updateNote(NoteEntity noteEntity)
	{
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MyDatabase.KEY_NAME, noteEntity.getName());
		values.put(MyDatabase.KEY_DESC, noteEntity.getDescription());
		values.put(MyDatabase.KEY_PATH, noteEntity.getPath());
	
		return db.update(MyDatabase.TABLE_NOTES,
				values,
				MyDatabase.KEY_ID+"=?",
				new String[]{String.valueOf(noteEntity.getId())});
	}
  ```
 6. Borrar una nota
 
  ```
	public int deleteNote(NoteEntity noteEntity)
	{
		 SQLiteDatabase db = helper.getWritableDatabase(); 
		 int row= db.delete(MyDatabase.TABLE_NOTES,
				 MyDatabase.KEY_ID+"=?", 
				 new String[]{String.valueOf(noteEntity.getId())});
		 db.close();
		return row;
	}
  ```
 
#### Listar Notas
 MainActivity.java

- Crear una instancia de CRUDOperations
- Con el método "getAllNotes()", solicitar el listado de notas de la BD local.
- Ojo que la primera vez saldrá vacio sino se tiene data precargada.
- El método "getAllNotes()" nos devuelve un List que usamos para llenar la lista mediante el adapter "NoteAdapter"

```
     private void loadData() {
        crudOperations= new CRUDOperations(new MyDatabase(this));
        lsNoteEntities= crudOperations.getAllNotes();
        noteAdapter= new NoteAdapter(this,lsNoteEntities);
        lstNotes.setAdapter(noteAdapter);

    }
```

#### Data precargada
 - Ejecutar solo la primera vez para cargar una data inicial, luego comentar el método y volver a compilar

```
 private void populate() {

        CRUDOperations crudOperations= new CRUDOperations(new MyDatabase(this));
        crudOperations.addNote(new NoteEntity("Mi Nota","Esta es un nota ",null));
        crudOperations.addNote(new NoteEntity("Segunda Nota","Esta es la segunds nota ",null));
        crudOperations.addNote(new NoteEntity("Tercera Nota","Esta es la tercera nota ",null));
        crudOperations.addNote(new NoteEntity("Cuarta Nota","Esta es la cuarta nota ",null));
        crudOperations.addNote(new NoteEntity("Quinta Nota","Esta es la quinta nota ",null));
        crudOperations.addNote(new NoteEntity("Sexta Nota","Esta es la sexta nota ",null));

        Log.v(TAG, "populate " + crudOperations.getAllNotes());
    }
```

### Agregar una nota : Base de Datos integrado con las Vistas
 AddNoteFragment.java [com.isil.mynotes.view.fragments.AddNoteFragment]
 - Instanciar los elementos de la UI
```
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        eteName=(EditText)getView().findViewById(R.id.eteName);
        eteDesc=(EditText)getView().findViewById(R.id.eteDesc);
        eteNote=(EditText)getView().findViewById(R.id.eteNote);
        btnAddNote=(Button)getView().findViewById(R.id.btnAddNote);

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });
    }
```
- Método Agregar Nota

 	- Extraer los campos ingresados por el usuario
 	- Crear un entidad "NoteEntity" con la información ingresada
 	- Llamar al crudoperation de la actividad principal e invocar el método "addNote()" donde enviamos la entidad.
 	- Cerrar la vista actual
```
  private void addNote() {
        name= eteName.getText().toString().trim();
        desc= eteDesc.getText().toString().trim();
        note= eteNote.getText().toString().trim();

        NoteEntity noteEntity= new NoteEntity(name,desc,null);
        mListener.getCrudOperations().addNote(noteEntity);

        getActivity().finish();

    }
```


 
 


  

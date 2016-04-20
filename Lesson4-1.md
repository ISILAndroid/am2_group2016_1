## Persistencia de Datos (S4-S6)

  - Base de datos Sqlite
  - Ejemplos , CRUD
  
### Aplicación MyNotes

![listado de notas](https://github.com/ISILAndroid/am2_group2016_1/blob/Lesson4/screenshots/Listado%20de%20Notas.png)

#### Listado de Notas
 - MainActivity.java 
 
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
 
 ```
   private void loadData() {
        crudOperations= new CRUDOperations(new MyDatabase(this));
        lsNoteEntities= crudOperations.getAllNotes();
        noteAdapter= new NoteAdapter(this,lsNoteEntities);
        lstNotes.setAdapter(noteAdapter);

    }
 ```
 
#### Agregar Notas
#### Editar Notas
#### Eliminar Notas
 


  

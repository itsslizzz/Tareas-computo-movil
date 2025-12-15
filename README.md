Repo con las tareas:
- Tarea1 (la primera de las 3 tareas, con fecha de entrega para 30/11)
- Tarea 2
- Tarea 3
- Tarea_Formulario
- Tipos de diseño
- imc

Nombre del proyecto:Tarea 1 app de tareas
Breve descripción: Aplicación Android sencilla para gestionar tareas personales
Permite:
- Agregar nuevas tareas
- Ver la lista de tareas en tarjetas (RecyclerView)
- Marcar una tarea como completada o pendiente
- Eliminar tareas de la base de datos
- Editar una tarea (nombre y descripcion)

 Instrucciones de instalación y ejecución: 
 1- clonar este repo de github 
 2- Elegir la carpeta del proyecto correspondiente (llamada tarea1)
 3- Botón Run  en Android Studio
 4- Elegir el dispositivo / emulador

 Una vez instalada, el usuario vera la pantalla principal con las cards de las tareas, al principio tendra que crear su primera tarea, con el simbolo de + que esta situado 
 en la parte inferior derecha, tiene que escribir nombre de la tarea y descripcion, posteriormente marcar si esta completada o no con un checkbox
 la fecha se guarda en automatico (no se pueden dejar nombre y descripcion vacios). Posteriormente en la pantalla principal se mostrara la card con 
 la tarea y abajo de cada tarea aparecen la opcion de "borrar" y "editar".

 Estructura de la base de datos:
 Table: Task
 int id primary key
 string task_description 
 string created_at 
 boolean is_completed  (0 si no esta completa, 1 si si)
 TaskDao con:
 insert(Task task)
 update(Task task)
 delete(Task task)
 getAllTasks()
 TaskController: maneja la logica basica llamando al dao y realizando las operaciones
 Para separar responsabilidades se uso MVC (modelo vista controlador) 
 

 <img width="335" height="723" alt="image" src="https://github.com/user-attachments/assets/3fa84048-9198-453e-996f-df9ab4f8577b" />
 <img width="325" height="720" alt="image" src="https://github.com/user-attachments/assets/a8a34136-9318-4cac-84c2-478906d034d2" />
 <img width="325" height="720" alt="image" src="https://github.com/user-attachments/assets/d2cac31a-d89c-41c8-8860-b19bc1401fa2" />
 <img width="327" height="723" alt="image" src="https://github.com/user-attachments/assets/630927a6-8576-44f3-84a4-984862acbd02" />
 <img width="338" height="727" alt="image" src="https://github.com/user-attachments/assets/11d059eb-aa15-490c-b2b7-c33e6e5b989f" /> cambiando la tarea de interfaz a programacion
 <img width="321" height="718" alt="image" src="https://github.com/user-attachments/assets/d2a4f6c8-1642-424b-816b-43ca07f4eeef" /> cambio realizado
 <img width="164" height="358" alt="image" src="https://github.com/user-attachments/assets/e0596274-9cd9-4e3a-b9c7-85e78e474c5b" /> borrar tarea de programacion
------------------------------------------------------------------------------------------------------------------------------------------------------------

Nombre del proyecto:Tarea 2 Notebook
Breve descripción: Aplicación Android sencilla para crear categorias con sus notas 1:N
Permite:
- Agregar una categoria
- Agregar Notas a las categorias 
- Filtrar notas por categoria / buscar una nota con base a su titulo o contenido

Instrucciones de instalación y ejecución: 
 1- clonar este repo de github 
 2- Elegir la carpeta del proyecto correspondiente (llamada tarea2)
 3- Botón Run  en Android Studio
 4- Elegir el dispositivo / emulador

 Una vez instalada, el usuario vera la pantalla principal vacia, tendra que agregar una categoria, una vez creada, puede añadir una nota en la que tiene que llenar datos como (titulo, contenido, fecha y asociar esa nota a una categoria existente). En la pantalla principal se mostraran las cards con los datos de la nota y la categoria a la que pertenece. El usuario puede filtrar las notas por una categoria (en el spinner se muestran las categorias y una vez que seleccione una, el sistema muestra todas las notas de esa categoria) o bien, buscar una nota en la barra de busqueda ya sea por el titulo o contenido de la nota. 

 Estructura de la base de datos:
 @Entity  (tableName = "Categories")
public class Category {

    @PrimaryKey (autoGenerate = true)
    public int category_id;

    @ColumnInfo (name = "category_name")
    public String category_name;


 @Entity (tableName = "Notes", foreignKeys = @ForeignKey
        (entity = Category.class, parentColumns = "category_id",
        childColumns = "category_id"))
public class Note {
    @PrimaryKey (autoGenerate = true)
    public int note_id;

    @ColumnInfo (name = "note_title")
    public String note_title;
    @ColumnInfo (name = "category_id")
    public int category_id;
    @ColumnInfo (name = "note_content")
    public String note_content;

    @ColumnInfo (name = "created_at")
    public  String created_at;

 como podemos ver, se muestra una relacion de 1:N, es decir, una categoria puede tener varias notas.
 Para separar responsabilidades se uso MVC (modelo vista controlador) 

 App sin categorias y notas 
 <img width="330" height="719" alt="image" src="https://github.com/user-attachments/assets/8e634944-a045-452f-a72b-71a3cae89db0" />
 Añadir una categoria (computo movil)
 <img width="349" height="721" alt="image" src="https://github.com/user-attachments/assets/c7a597ca-ec50-49fe-b7f4-36a1171f2900" />
 Añadir una nota (asociada a computo movil)
<img width="336" height="611" alt="image" src="https://github.com/user-attachments/assets/000faa36-0d75-4f8e-bc9a-44b7bbfe84d8" />
Nota agregada 
<img width="306" height="690" alt="image" src="https://github.com/user-attachments/assets/76b185bd-88d1-4000-a4ca-87860ae18cb4" />
Mas categorias 
<img width="336" height="647" alt="image" src="https://github.com/user-attachments/assets/0a65a507-9bea-4bd5-9c77-1358a158150c" />
Mas notas
<img width="348" height="676" alt="image" src="https://github.com/user-attachments/assets/d4aac760-cb6f-4da2-8422-5ca7d8769fbb" />
Mostrar solo una categoria (las de computo movil)
<img width="331" height="725" alt="image" src="https://github.com/user-attachments/assets/92ef1acd-7768-4f3c-b316-664b4c1cf30c" />
Mostrar con barra de busqueda con LIKE ("tarea 3")
<img width="327" height="686" alt="image" src="https://github.com/user-attachments/assets/2866e874-b783-43eb-97e2-1c5e99912885" />

----------------------------------------------------------------------------------------------------------------------------------------------------

Nombre del proyecto: Tarea 3 - Action History Application

Breve descripción: Aplicación Android sencilla para gestionar tareas personales con un sistema de auditoría de cambios.

Permite:
- Agregar nuevas tareas.

- Ver la lista de tareas en tarjetas (RecyclerView).

- Marcar una tarea como completada o pendiente.

- Eliminar tareas de la base de datos.

- Editar una tarea (nombre y descripción).

- Visualizar un historial de acciones (Auditoría).

- Filtrar el historial por fecha (Calendario) y tipo de acción (Crear, Actualizar, Eliminar).


Uso de la aplicación: En la pantalla principal, hay un botón de Historial. Al pulsarlo, lleva a una nueva pantalla donde se listan todos los cambios realizados en la app. Cada registro muestra qué acción se hizo (Insertar, Actualizar o Eliminar), el nombre de la tarea afectada y la fecha/hora.
El usuario puede filtrar estos registros seleccionando una fecha en el CalendarView o eligiendo un tipo de acción en el menú desplegable (Spinner).


Estructura de la base de datos (Room):

Table: Task
int id (Primary Key, auto-generated)
string task_description
string task_title
string created_at
boolean is_completed (0 false, 1 true)

Table: History (Nueva)
int id (Primary Key, auto-generated)
string action (Códigos internos: insert_task, update_task, delete_task)
string details (Guarda el título de la tarea afectada)
string created_at (Fecha y hora de la acción)

Componentes Técnicos (MVC):
- TaskDao: Métodos estándar CRUD (insert, update, delete, getAllTasks).
- HistoryDao: Métodos para consultar el historial ordenado por fecha descendente.
- TaskController: Maneja la lógica de las tareas. Al realizar una acción (guardar, editar, borrar), llama automáticamente al HistoryController para registrar el evento.
- HistoryController: Se encarga de guardar los logs de auditoría en segundo plano y de filtrar la lista para la vista de Historial.

Se utiliza ExecutorService y Handler para realizar todas las operaciones de base de datos en segundo plano sin congelar la interfaz de usuario, ya que era algo que pasaba frecuentemente al momento de intentar realizar algo. 



<img width="396" height="787" alt="image" src="https://github.com/user-attachments/assets/849f3473-9178-447d-b1cb-e8610f9782f9" />
<img width="375" height="782" alt="image" src="https://github.com/user-attachments/assets/2881fda7-5ffd-48d0-9f87-670e02a292fb" />
<img width="372" height="783" alt="image" src="https://github.com/user-attachments/assets/1040b191-de3f-44a7-ae03-eebfcbefd145" /> filtrar por eliminar
<img width="379" height="778" alt="image" src="https://github.com/user-attachments/assets/7637f7e7-20a4-4852-93f7-dd684373d41f" />  filtrar por actualizar
<img width="355" height="776" alt="image" src="https://github.com/user-attachments/assets/7a7fcfe8-8083-4442-b80e-7b2a4712737a" /> filtrar por un dia donde no se hizo una accion




 


 




 




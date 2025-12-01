Repo con las tareas:
- Tarea1 (la primera de las 3 tareas, con fecha de entrega para 30/11)
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



 




 




package com.fic.tarea2.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fic.tarea2.R;

import java.util.ArrayList;
import java.util.List;

import controller.CategoryController;
import controller.NoteController;
import model.Category;
import model.Note;
import view.NoteAdapter;

public class MainActivity extends AppCompatActivity {
    Spinner spinnerCategoria;
    ImageButton ibCategoria;
    ImageButton ibNota;
    private CategoryController categoryController;
    private NoteController noteController;
    private RecyclerView rvNotes;
    private NoteAdapter noteAdapter;
    SearchView svBuscar;

    private List<Category> listaCategoriasGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        ibCategoria = findViewById(R.id.ibCategoria);
        ibNota = findViewById(R.id.ibNota);
        rvNotes = findViewById(R.id.rvNotes);
        svBuscar= findViewById(R.id.svBuscar);

        categoryController = new CategoryController(this);
        noteController = new NoteController(this);

        rvNotes.setLayoutManager(new LinearLayoutManager(this));

        ibCategoria.setOnClickListener(v -> addCategoryAlert());
        ibNota.setOnClickListener(v -> addNoteAlert());

        showCategoriesSpinner();
        updateList(0);

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    updateList(0);
                } else {
                    int categoryId = listaCategoriasGlobal.get(position - 1).category_id;
                    updateList(categoryId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        svBuscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getByText(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getByText(newText);
                return false;
            }
        });
    }

    private void showCategoriesSpinner() {
        listaCategoriasGlobal = categoryController.getAllCategories();
        List<String> nombresCategorias = new ArrayList<>();

        nombresCategorias.add("Todas las notas");

        for (Category cat : listaCategoriasGlobal) {
            nombresCategorias.add(cat.category_name);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, nombresCategorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);
    }

    private void updateList(int categoryId) {
        List<Note> notas;
        if (categoryId == 0) {
            notas = noteController.getAllNotes();
        } else {
            notas = noteController.getAllNotesByCateg(categoryId);
        }
        noteAdapter = new NoteAdapter(notas, this);
        rvNotes.setAdapter(noteAdapter);
    }


    private void addCategoryAlert() {
        View dialogView = getLayoutInflater().inflate(R.layout.activity_agregar_category, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        EditText etNombre = dialogView.findViewById(R.id.etNombreCategoria);
        Button btnAgregar = dialogView.findViewById(R.id.btnAgregarTarea);

        btnAgregar.setOnClickListener(v -> {
            String nombreCat = etNombre.getText().toString().trim();
            if (!nombreCat.isEmpty()) {
                categoryController.addCategory(nombreCat);
                Toast.makeText(this, "Categoría guardada", Toast.LENGTH_SHORT).show();

                showCategoriesSpinner();
                dialog.dismiss();
            } else {
                etNombre.setError("Escribe un nombre");
            }
        });
        dialog.show();
    }

    private void addNoteAlert() {
        View dialogView = getLayoutInflater().inflate(R.layout.activity_agregar_nota, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        EditText etTitulo = dialogView.findViewById(R.id.etTituloNota);
        EditText etContenido = dialogView.findViewById(R.id.etContenidoNota);
        EditText etFecha = dialogView.findViewById(R.id.etFechaNota);
        Spinner spinnerDialogo = dialogView.findViewById(R.id.spinnerCategoria);
        Button btnGuardar = dialogView.findViewById(R.id.btnAgregarTarea);

        List<Category> catsDialogo = categoryController.getAllCategories();
        List<String> nombres = new ArrayList<>();
        for (Category c : catsDialogo) {
            nombres.add(c.category_name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, nombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDialogo.setAdapter(adapter);

        btnGuardar.setOnClickListener(v -> {
            String titulo = etTitulo.getText().toString();
            String contenido = etContenido.getText().toString();
            String fecha = etFecha.getText().toString();

            int posicion = spinnerDialogo.getSelectedItemPosition();
            if (posicion >= 0 && !catsDialogo.isEmpty()) {
                int idCat = catsDialogo.get(posicion).category_id;

                if (!titulo.isEmpty()) {
                    noteController.insertNote(titulo, idCat, contenido, fecha);
                    Toast.makeText(this, "Nota guardada", Toast.LENGTH_SHORT).show();

                    spinnerCategoria.setSelection(0);
                    updateList(0);

                    dialog.dismiss();
                } else {
                    etTitulo.setError("Falta título");
                }
            } else {
                Toast.makeText(this, "Crea una categoría primero", Toast.LENGTH_LONG).show();
            }
        });
        dialog.show();
    }

    private void getByText(String texto) {
        if (texto.isEmpty()) {
            updateList(0);
        } else {
            String query = "%" + texto + "%";
            List<Note> notasFiltradas = noteController.getNoteByTitleOrContent(query);
            noteAdapter = new NoteAdapter(notasFiltradas, this);
            rvNotes.setAdapter(noteAdapter);
        }
    }
}


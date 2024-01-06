package com.noteboard.api.inicialization;

import java.time.LocalDateTime;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.noteboard.api.persistence.entity.Category;
import com.noteboard.api.persistence.entity.Note;
import com.noteboard.api.persistence.entity.NoteStatus;
import com.noteboard.api.persistence.repository.CategoryRepository;
import com.noteboard.api.persistence.repository.NoteRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DataInitializer implements InitializingBean {
    private final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public void afterPropertiesSet() throws Exception {
        // categories
        createCategoryIfNotExists("Universidad", (long) 1);
        createCategoryIfNotExists("Trabajo", (long) 2);
        createCategoryIfNotExists("Familia",(long) 3 );

        // notes
        createNoteIfNotExists("Reunión familiar este domingo", "Recordar llevar las fotos antiguas para compartir con todos.", 3L);
        createNoteIfNotExists("Horario de estudio para exámenes", "Organizar un horario de estudio para los exámenes finales. Incluir revisiones de matemáticas los lunes y miércoles, y de historia los martes y jueves. Reservar los viernes para repasar los temas más difíciles.", 1L);
        createNoteIfNotExists("Proyecto de biología", "Completar el proyecto de biología sobre el ecosistema marino. Investigar sobre las especies endémicas de la región y sus interacciones ecológicas. Preparar una presentación para el próximo miércoles.", 1L);
        createNoteIfNotExists("Reunión con el equipo de marketing", "Programar una reunión con el equipo de marketing para discutir la nueva campaña publicitaria. Revisar las métricas de la última campaña y proponer mejoras basadas en los datos recogidos.", 2L);
        createNoteIfNotExists("Actualización del software de gestión", "Coordinar con el departamento de TI para actualizar el software de gestión de proyectos. Asegurarse de que todas las funciones necesarias estén incluidas y de que el equipo reciba la formación adecuada para su uso.", 2L);
        createNoteIfNotExists("Planificación de vacaciones", "Comenzar a planificar las vacaciones familiares de verano. Considerar destinos que sean adecuados para todas las edades y revisar opciones de alojamiento. Establecer un presupuesto y proponer actividades.", 3L);
        createNoteIfNotExists("Recetas para la cena de aniversario", "Buscar recetas especiales para la cena de aniversario. Incluir un entrante ligero, un plato principal innovador y un postre delicioso. Hacer una lista de compras para asegurar tener todos los ingredientes necesarios.", 3L);
        createNoteIfNotExists("Realizar actividad de Algoritmos y Estructuras de Datos", "Realizar la actividad de Algoritmos y Estructuras de Datos, la cual consiste en realizar un programa que permita realizar operaciones con matrices.", 1L); 
    }

    private void createCategoryIfNotExists(String name, Long id) {
        if (!categoryRepository.existsByName(name)) {
            Category category = new Category();
            category.setName(name);
            category.setId(id);
            categoryRepository.save(category);
        }
    }

    private void createNoteIfNotExists(String title, String content, Long categoryId) {
        if (!noteRepository.existsByTitle(title)) {
            Category category = categoryRepository.findById(categoryId).orElseThrow();
            Note note = new Note();
            note.setTitle(title);
            note.setContent(content);
            note.setCategory(category);
            note.setCreationDate(LocalDateTime.now());
            note.setStatus(NoteStatus.ACTIVE);
            noteRepository.save(note);
        }
    }

    
}

<template>
  <Header
    @create-note="toggleCreateNoteModal"
    @create-category="toggleCreateCategoryModal"
    :categories="categories"
    @filter-by-category ="filterNotesByCategory"
    @filter-archived="filterArchivedNotes"
    @filter-active="filterActiveNotes"
  ></Header>
  <CreateNote
    :isVisible="showCreateNoteModal"
    @close="toggleCreateNoteModal(false)"
    :categories="categories"
    @note-saved="reloadNotes"
    :note="currentNote"
  ></CreateNote>
  <CreateCategory
    :isVisible="showCreateCategoryModal"
    @close="toggleCreateCategoryModal(false)"
    @category-saved="reloadCategories"
  ></CreateCategory>

  <div class="components-wrapper">
    <Note
      v-for="note in notes"
      :key="note.id"
      :id="note.id"
      :titulo="note.title"
      :contenido="note.content"
      :categoria="getCategoria(note.categoryId)"
      :estado="note.status"
      @editar="handleEditar(note.id)"
      @eliminar="handleEliminar"
      @cambiarEstado="handleCambiarEstado"
    ></Note>
  </div>
</template>

<script>
import Note from "./components/Note.vue";
import Header from "./components/Header.vue";
import CreateNote from "./components/CreateNote.vue";
import CreateCategory from "./components/CreateCategory.vue";

export default {
  name: "App",
  components: {
    Note,
    Header,
    CreateNote,
    CreateCategory,
  },
  data() {
    return {
      notes: [],
      categories: {},
      showCreateNoteModal: false,
      showCreateCategoryModal: false,
    };
  },
  mounted() {
    Promise.all([
      fetch("https://pacific-diode-406412.rj.r.appspot.com/api/notes/all"),
      fetch("https://pacific-diode-406412.rj.r.appspot.com/api/categories/all"),
    ])
      .then((responses) => Promise.all(responses.map((res) => res.json())))
      .then(([notesData, categoriesData]) => {
        this.notes = notesData;
        this.categories = categoriesData.reduce((acc, category) => {
          acc[category.id] = category.name;
          return acc;
        }, {});
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  },
  methods: {
    getCategoria(categoryId) {
      return this.categories[categoryId] || "General";
    },
    handleEditar(id) {
      const noteToEdit = this.notes.find((note) => note.id === id);
      if (noteToEdit) {
        this.currentNote = { ...noteToEdit }; // Copia de la nota
        this.showCreateNoteModal = true;
      }
    },
    handleEliminar(id) {
      this.notes = this.notes.filter((note) => note.id !== id);
    },
    toggleCreateNoteModal(state = true) {
      this.showCreateNoteModal = state;
    },
    toggleCreateCategoryModal(state = true) {
      this.showCreateCategoryModal = state;
    },
    reloadNotes() {
      fetch("https://pacific-diode-406412.rj.r.appspot.com/api/notes/all")
        .then((response) => response.json())
        .then((notes) => {
          this.notes = notes;
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
    reloadCategories() {
      fetch("https://pacific-diode-406412.rj.r.appspot.com/api/categories/all")
        .then((response) => response.json())
        .then((categories) => {
          this.categories = categories.reduce((acc, category) => {
            acc[category.id] = category.name;
            return acc;
          }, {});
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
    filterNotesByCategory(categoryId) {
      let url;
      if (categoryId) {
        url = `https://pacific-diode-406412.rj.r.appspot.com/api/notes/category/${categoryId}`;
      } else {
        url = `https://pacific-diode-406412.rj.r.appspot.com/api/notes/all`;
      }

      console.log(url);

      fetch(url)
        .then((response) => response.json())
        .then((notes) => {
          this.notes = notes;
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
    filterArchivedNotes() {
      fetch(`https://pacific-diode-406412.rj.r.appspot.com/api/notes/archived`)
        .then((response) => response.json())
        .then((notes) => {
          this.notes = notes;
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
    filterActiveNotes() {
      fetch(`https://pacific-diode-406412.rj.r.appspot.com/api/notes/active`)
        .then((response) => response.json())
        .then((notes) => {
          this.notes = notes;
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
    handleCambiarEstado() {
      this.reloadNotes();
    },

  },
};
</script>

<style>
.components-wrapper {
  width: 95%;
  margin: 0 auto;
  display: flex;
  justify-content: flex-start;
  flex-wrap: wrap;
  align-items: flex-start;
}
</style>

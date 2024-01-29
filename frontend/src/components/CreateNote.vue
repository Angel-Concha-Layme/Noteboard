<template>
  <div class="modal" v-if="isVisible">
    <div class="modal-content">
      <h3>Título</h3>
      <input type="text" placeholder="Título de la nota" v-model="noteTitle" />
      <h3>Contenido</h3>
      <textarea
        placeholder="Contenido de la nota"
        v-model="noteContent"
      ></textarea>
      <h3>Categoria</h3>

      <div class="footer">
        <select class="filter" v-model="selectedCategoryId">
          <option disabled value="">Selecciona una categoría</option>
          <option
            v-for="(categoryName, categoryId) in categories"
            :key="categoryId"
            :value="categoryId"
          >
            {{ categoryName }}
          </option>
        </select>
        <div class="options">
          <button class="guardar" @click="saveNote">Guardar</button>
          <button class="cerrar" @click="closeModal">Cerrar</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      noteTitle: "",
      noteContent: "",
      selectedCategoryId: "",
    };
  },
  props: {
    isVisible: Boolean,
    note: {
      type: Object,
      default: () => ({
        id: null,
        title: "",
        content: "",
        categoryId: null,
      }),
    },
    categories: {
      type: Object,
      default: () => ({}),
    },
  },
  mounted() {
    window.addEventListener("keydown", this.handleEscapePress);
  },
  beforeDestroy() {
    window.removeEventListener("keydown", this.handleEscapePress);
  },

  data(){
    return{
      noteTitle: this.note.title,
      noteContent: this.note.content,
      selectedCategoryId: this.note.categoryId,
    };
  },

  watch:{
    note :{
      handler(newVal){
        this.noteTitle = newVal.title;
        this.noteContent = newVal.content;
        this.selectedCategoryId = newVal.categoryId;
      },
      deep: true
    },
  },
  methods: {
    saveNote() {
      const url = this.note.id
        ? `https://noteboard-411207.rj.r.appspot.com/api/notes/${this.note.id}`
        : "https://noteboard-411207.rj.r.appspot.com/api/notes";

      const method = this.note.id ? "PUT" : "POST";
      const noteData = {
        title: this.noteTitle,
        content: this.noteContent,
        categoryId: this.selectedCategoryId,
      };

      fetch(url, {
        method: method,
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(noteData),
      })
        .then((response) => response.json())
        .then((note) => {
          this.$emit("note-created", note); 
          this.closeModal();
          this.$emit("note-saved");
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
    closeModal() {
      this.$emit("close");
    },
    handleEscapePress(event) {
      if (event.key === "Escape") {
        this.closeModal();
      }
    },
  },
};
</script>

<style scoped>
.modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-content {
  background: white;
  padding-left: 20px;
  padding-right: 20px;
  padding-top: 10px;
  padding-bottom: 10px;

  border-radius: 5px;
  font-size: 25px;
  width: 700px;
  height: 540px;
}

.modal-content input {
  width: 100%;
  height: 30px;
  border-radius: 5px;
  border: 1px solid #ccc;
  font-size: 20px;
}

.modal-content textarea {
  width: 100%;
  height: 150px;
  border-radius: 5px;
  border: 1px solid #ccc;
  font-size: 20px;
}

.footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

select {
  width: 50%;
  height: 30px;
  border-radius: 5px;
  border: 1px solid #ccc;
  font-size: 20px;
}
.options button {
  background-color: #4caf50;
  color: white;
  padding: 10px 10px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  border: none;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
  transition: all 0.3s ease 0s;
}

.options button.guardar {
  background-color: #4caf50;
}

.options.button.guardar:hover {
  background-color: #3e8e41;
}

.options button.cerrar {
  background-color: #f44336;
}

.options.button.cerrar:hover {
  background-color: #d32d26;
}
</style>

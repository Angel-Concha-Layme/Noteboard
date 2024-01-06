<template>
  <div class="modal" v-if="isVisible">
    <div class="modal-content">
      <h3>Categoría</h3>
      <input
        type="text"
        placeholder="Nombre de la categoría"
        v-model="categoryName"
      />
      <div class="footer">
        <div class="options">
          <button class="guardar" @click="saveCategory">Guardar</button>
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
      categoryName: "",
    };
  },
  props: {
    isVisible: Boolean,
    category: {
      type: Object,
      default: () => ({
        id: null,
        name: "",
      }),
    },
  },
  mounted() {
    window.addEventListener('keydown', this.handleEscapePress);
  },
  beforeDestroy() {
    window.removeEventListener('keydown', this.handleEscapePress);
  },
  methods: {
    saveCategory() {
      const url = "https://pacific-diode-406412.rj.r.appspot.com/api/categories";
      const data = {
        name: this.categoryName,
      };

      fetch(url, {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
          "Content-Type": "application/json",
        },
      })
        .then((res) => res.json())
        .catch((error) => console.error("Error:", error))
        .then((response) => console.log("Success:", response));

      this.$emit("category-saved");
      this.closeModal();
      this.$emit("category-saved");
    },
    closeModal() {
      this.$emit("close");
    },
    handleEscapePress(event) {
      if (event.key === 'Escape') {
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
  height: 200px;
}
.modal-content input {
  width: 100%;
  height: 30px;
  border-radius: 5px;
  border: 1px solid #ccc;
  font-size: 20px;
  margin-bottom: 20px;
}
.footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
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

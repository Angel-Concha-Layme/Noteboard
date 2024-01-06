<template>
  <div class="component-container">
    <div class="header">
      <h2>{{ titulo }}</h2>
    </div>
    <div class="content">
      <textarea readonly class="contenido-textarea">{{ contenido }}</textarea>
    </div>
    <div class="category">
      <span class="categoria">{{ categoria }}</span>
    </div>
    <div class="footer">
      <div class="container">
        <span class="state">{{ estado }}</span>
      <button @click="cambiarEstado">Cambiar estado</button>
      </div>
      <div class="buttons">
        <button class="editar" @click="editar">Editar</button>
        <button class="eliminar" @click="eliminar">Eliminar</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Note",
  props: {
    id: Number,
    titulo: String,
    contenido: String,
    categoria: String,
    estado: String,
  },
  methods: {
    editar() {
      this.$emit("editar");
    },
    eliminar() {
      fetch(`https://pacific-diode-406412.rj.r.appspot.com/api/notes/${this.id}`, {
        method: "DELETE",
      })
        .then((response) => {
          if (response.ok) {
            this.$emit("eliminar", this.id);
          } else {
            console.error("Error al eliminar la nota");
          }
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
    cambiarEstado() {
      let url;
      console.log(this.estado);
      if (this.estado === "ACTIVE") {
        url = `https://pacific-diode-406412.rj.r.appspot.com/api/notes/${this.id}/archive`;
      } else {
        url = `https://pacific-diode-406412.rj.r.appspot.com/api/notes/${this.id}/active`;
      }

      console.log(url);

      fetch(url, {
        method: "PUT",
      })
        .then((response) => {
          if (response.ok) {
            this.$emit("cambiarEstado", this.id);
          } else {
            console.error("Error al cambiar el estado de la nota");
          }
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
  },
};
</script>

<style scoped>
.component-container {
  border-radius: 8px;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
  border: 1px solid #717171;
  padding: 16px;
  margin: 10px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 390px;
  height: 480px;
  background-color: #2c3e50;
  color: white;
  transition: all 0.3s ease 0s;
}

.component-container:hover {
  border: 2px solid #ffffff;
}

.header {
  font-size: 20px;
  font-weight: bold;
  display: flex;
  justify-content: center;
  align-items: center;
  
}

.content {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px;
  flex-grow: 1;
}

.category {
  font-size: 20px;
  display: flex;
  justify-content: center;
  padding: 10px 0;
  border: 1px solid #ccc;
  margin-bottom: 30px;
  background-color: beige;
  color: black;
  font-weight: 500;
}

.footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.footer button {
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

.footer button:hover {
  background-color: #3e8e41;
}
.footer button.editar {
  background-color: #008cba;
}

.footer button.editar:hover {
  background-color: #007a99;
}

.footer button.eliminar {
  background-color: #f44336;
}

.footer button.eliminar:hover {
  background-color: #d32d26;
}

.contenido-textarea {
  width: calc(100% - 10px);
  height: auto;
  min-height: 170px;
  border: none;
  resize: none;
  margin: 5px 0;
  padding: 5px;
  font-size: 16px;
  overflow-y: auto;
  outline: none;
  background-color: #2c3e50;
  color: white;
}

.state {
  color: white;
  background-color: black;
  padding: 10px 10px;
  margin: 4px 2px;
}
</style>

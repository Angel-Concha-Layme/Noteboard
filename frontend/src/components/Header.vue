<template>
  <header class="header">
    <div class="create-note-category">
      <button class="button" @click="emitCreateNote">Crear nota +</button>
      <button class="button" @click="emitCreateCategory">
        Crear categoría +
      </button>
    </div>

    <div class="filters">
      <select class="filter" @change="filterByCategory($event.target.value)">
        <option value="">Todas las categorías</option>
        <option
          v-for="(categoryName, categoryId) in categories"
          :key="categoryId"
          :value="categoryId"
        >
          {{ categoryName }}
        </option>
      </select>
      <button class="button" @click="filterArchived">Notas archivadas</button>
    <button class="button" @click="filterActive">Notas activas</button>
    </div>
  </header>
  
</template>

<script>
export default {
  name: "Header",
  props: {
    categories: {
      type: Object,
      default: () => ({}),
    },
  },
  methods: {
    emitCreateNote() {
      this.$emit("create-note");
    },
    emitCreateCategory() {
      this.$emit("create-category");
    },
    filterByCategory(categoryId) {
      this.$emit("filter-by-category", categoryId);
    },
    filterArchived() {
      this.$emit("filter-archived");
    },
    filterActive() {
      this.$emit("filter-active");
    },
  },
};
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border-bottom: 1px solid #ffffff;
  padding-bottom: 20px;
  margin-bottom: 10px;
}

.button {
  padding: 5px 10px;
  margin: 0 5px;
  background-color: #2c3e50;
  border: 1px solid #000;
  cursor: pointer;
  font-size: 24px;
  color: #fff;
  transition: all 0.3s ease;
}

.button:hover {
  background-color: #34495e;

  transform: scale(1.1);
}

.filters {
  display: flex;
}

.filter {
  padding: 5px 10px;
  margin-right: 5px;
  font-size: 24px;
  background-color: #2c3e50;
  border: 1px solid #000;
  color: #fff;
}
</style>

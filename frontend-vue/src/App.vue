<template>
  <div id="app">
    <div class="ui inverted segment navbar">
      <div class="ui left aligned container">
        <div class="ui large secondary inverted pointing menu compact">
          <h2 class="ui header inverted">
            販売管理システム
            <div class="ui sub header">SalesWorker</div>
          </h2>
          <router-link to="/estimates" exact class="item">
            <i class="tasks icon"></i> 見積検索
          </router-link>
          <router-link to="/estimates/new" class="item">
            <i class="plus circle icon"></i> 見積登録
          </router-link>
        </div>
      </div>
    </div>

    <div class="ui container">
      <div class="column">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "app"
};
</script>

<style>
@import "./semantic.css";
#app > div.navbar {
  margin-bottom: 1.5em;
}
/*h1 {
    margin-bottom: 1em;
}*/
.myFlash {
  width: 250px;
  margin: 10px;
  position: absolute;
  top: 50;
  right: 0;
}
/*input {
  width: 300px;
}*/
div.label {
  width: 12em;
}
div.label.long {
  width: 20em;
}
div.input {
  margin-bottom: 1em;
}
button.ui.button {
  /* margin-top: 15px; */
  /* display: block;*/
}
button.ui.mleft {
  margin-left: 1em;
}
#overlay {
  z-index: 1;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);

  display: flex;
  align-items: center;
  justify-content: center;
}
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
  transition: opacity 0.3s ease;
}
.modal-container {
  z-index: 2;
  width: 50%;
  padding: 1em;
  background: #fff;
  box-sizing: border-box;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
}
.modal-enter {
  opacity: 0;
}

.modal-leave-active {
  opacity: 0;
}

.modal-enter .modal-container,
.modal-leave-active .modal-container {
  transform: translateY(30px);
}
input.readonly, select.readonly {
  color: #999 !important;
  background-color: #f6f6f6 !important;
}
button.fixsizebutton {
  width: 9em;
}
.right {
  position: relative;
  right: 0;
  top: 10px;
}
.ui.horizontal.divider {
  padding: 2em 0 1em 0;
}
</style>
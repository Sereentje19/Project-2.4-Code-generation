import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import footer from './components/home/Footer.vue';
import header from './components/home/Header.vue';
import router from './router'

import './assets/css/main.css'

const app = createApp(App)

app.component('headerNavigation', header)
app.component('footerNavigation', footer)

app.use(createPinia())
app.use(router)

app.mount('#app')

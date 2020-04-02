import Vue from 'vue'
import Vuetify from 'vuetify'
import '@babel/polyfill'
import 'api/resource'
import router from 'router/router'
import App from 'pages/App.vue'
import store from 'store/store'
import { connect } from './util/ws'
import 'vuetify/dist/vuetify.min.css'
import * as Sentry from '@sentry/browser'
import * as Integrations from '@sentry/integrations'

Sentry.init({
    dsn: 'https://19f6d0f97b504b78a2ba549f42767ea9@sentry.io/5186898',
    integrations: [new Integrations.Vue({Vue, attachProps: true})],
})

Sentry.configureScope(scope => {
    scope.setUser({
        id: profile && profile.id,
        username: profile && profile.name
    })
})


if(profile) {
    connect()
}
Vue.use(Vuetify)

new Vue({
    el: '#app',
    store,
    router,
    vuetify: new Vuetify(),
    render: a => a(App)
})







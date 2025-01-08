//Hier findet die Authentifizierung des Users statt

import { defineStore } from 'pinia'
import axios from 'axios'
import { API_URL } from '@/api'

export const useAuthStore = defineStore('auth', {
    state: () => ({
        isInitialized: false, // dient dazu, um festzuhalten, ob initial nach dem Seitenreload die Benutzerdaten bereits geladen wurden
        user: null
        //Types müssen hier noch initalisiert werden 

    }),
 
    actions: {
        /**
         * Diese Funktion dient dazu, nach einem Seitenreload die Benutzerdaten einmal zu laden.
         * Wenn diese Funktion mehrfach aufgerufen wird, soll sie dennoch nur ein einziges Mal einen Request abschicken
         */
        async initialize() {
            if(!this.isInitialized) {
                /**
                 * "isInitialized" bekommt als Wert die Promise an sich, die hier aber noch nicht awaited wird.
                 * Damit wird bei einem erneuten Aufruf der Funktion true und verhindert, dass "loadUser" unabsichtlich erneut ausgeführt wird.
                 * Mit "catch" verhindert man, dass diese Promise einen Error wirft
                 */
                this.isInitialized = this.loadUser().catch(() => undefined)
            }
            /**
             * Hier wird erst die Promise awaited. Wenn die Funktion aufgerufen wird, wenn der Wert von "isInitialized" bereits true ist,
             * entspricht der Ausdruck "await true", was nichts macht.
             */
            await this.isInitialized
            this.isInitialized = true
        },
        async loadUser() {
            // Es soll kein Request abgeschickt wird, wenn kein JWT gespeichert ist.
            if(!localStorage.getItem('jwt')) {
                return
            }
            const {data} = await axios.get(API_URL + 'auth')
            this.applyAuthentication(data)
        },
        async login(credentials) {
            const {data} = await axios.post(API_URL + 'auth/login', credentials)
            this.applyAuthentication(data)
        },
        
        logout() {
            this.user = null
            localStorage.removeItem('jwt') // Zum Logout reicht es, wenn das Frontend den JWT "vergisst"
        },
        applyAuthentication({token, id, username, mail, roles}) { 
            localStorage.setItem('jwt', 'Bearer ' + token) // Hier wird der JWT dauerhaft unter dem Namen "jwt" (erster Parameter) gespeichert.
            this.user = {id, username, mail, roles}
            console.log('apply auth')
            console.log(this.user)
        }
    }
})


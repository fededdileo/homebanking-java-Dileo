const app = Vue.createApp({
    data() {
        return {
            clients: [],
            json: [],
            form: {
                firstName: '',
                lastName: '',
                email: '',
            }
        }
    }, created() {
        this.loadData();
    },
    methods: {
        loadData() {
            axios.get('/rest/clients')
                .then(resp => {
                    this.clients = resp.data._embedded.clients;
                    this.json = resp.data
                })
        },
        addClient() {
            axios.post('/rest/clients', this.form)
                .then((res) => {
                })
        },
    },
})
app.mount("#app")

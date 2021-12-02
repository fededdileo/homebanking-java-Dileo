const app = Vue.createApp({

    data() {
        return {
            clientes: [],
            name: "",
            lname: "",
            cards: [],
            cardsDebito: [],
            cardsCredito: [],
            color: "",
            type: "",
            isDeleteCardModal: false,
            deleteCardNumber: "",
            deleteCardId: "",
        }
    },
    created() {
        axios.get("/api/clients/current")
            .then(response => {
                console.log(response)
                this.clientes = response.data
                this.name = this.clientes.firstName
                this.lname = this.clientes.lastName
                this.cards = response.data.cards
                this.cards.sort((a, b) => a.id - b.id)
                this.cardsDebito = this.cards.filter(e => e.type == 'DEBIT')
                this.cardsCredito = this.cards.filter(e => e.type == 'CREDIT')
            })
            .catch(function (error) {
                if (error.response) {
                    console.log(error.response.data);
                    console.log(error.response.status);
                    console.log(error.response.headers);
                } else if (error.request) {
                    console.log(error.request);
                } else {
                    console.log('Error', error.message);
                }
                console.log(error.config);
            })


    },
    methods: {
        logout(){
            axios.post('/api/logout')
            .then(response => window.location.replace("/web/index.html"))
            .catch(e =>{
                console.log(e);
            });
        },
        createCard() {
            axios.post('/api/clients/current/cards',`type=${this.type}&color=${this.color}`,
                {
                    headers: {'content-type': 'application/x-www-form-urlencoded'}
                })
            .then(response => window.location.href = "/web/webPages/cards.html")
            .catch(e =>{
                console.log(e);
            })
        },
        deleteCard(card) {
            this.isDeleteCardModal = true
            this.deleteCardId = card.id
            this.deleteCardNumber = card.number
        },
        deleteCardCancel() {
            this.deleteCardId = ""
            this.deleteCardNumber = ""
        },
        cortarFecha(fecha) {
            return fecha.split("").splice(2, 5).join("")
        },
        deleteCard(id) {
            axios.delete('/api/cards/delete/' + id)
                .then(() => swal('Card deleted'))
                .then(() => location.reload())

                .catch(err => {
                    swal(err)
                    this.deleteCardCancel
                    location.reload()
                })
        },
        estaVencida(truDate) {
            //let hoy = new Date(Date.now()).toLocaleString().split(',')[0];
            let hoy = new Date().toJSON().slice(0, 10).split('-').join('-');
            if(truDate.valueOf() < hoy.valueOf()) {
                console.log(hoy, truDate);
                return true
            }
        },
    },

})
app.mount("#app")
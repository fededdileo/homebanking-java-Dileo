const app = Vue.createApp({
    data() {
        return {
            numberCard: "",
            cvv: "",
            amount: "",
            description: "",
            cards: []
        }
    },
    created() {
        axios.get("/api/clients/current")
            .then(response => {
                console.log(response)
                this.clientes = response.data
                this.cards = response.data.cards

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
        pay() {
            axios.post("/api/payments", {
                number: this.numberCard,
                cvv: this.cvv,
                amount: this.amount,
                description: this.description
            })
                .then(res => console.log("pagado"))
                .then(response => {
                    swal({
                        title: "Payment!",
                        icon: "success",
                    });
                })
                .then(response => window.location.href = "/web/webPages/accounts.html")
                .catch(err => swal("Error to realize the payment, please verify the amount"))
        },
        logout(){
            axios.post('/api/logout')
            .then(response => window.location.replace("/web/index.html"))
            .catch(e =>{
                console.log(e);
            });
        },
    },
    computed: {

    }
})
app.mount("#app")
const app = Vue.createApp({
    data() {
        return {
            clientes: [],
            name: "",
            lname: "",
            transfer: "",
            accounts: [],
            accountDebit: "",
            accountCredit: "",
            amount: [],
            desc: "",
            accountsFiltradas: []
        }
    },
    created() {
        axios.get("/api/clients/current")
            .then(response => {
                console.log(response)
                this.clientes = response.data
                this.name = this.clientes.firstName
                this.lname = this.clientes.lastName
                this.accounts = this.clientes.accounts
                
            })
            .catch(error => {
                console.log(e);
            })
    },
    methods: {
        transferir() {
            axios.post('/api/clients/current/accounts/transactions',"amount=" + this.amount + "&description=" + this.desc + "&originAccount=" + this.accountDebit + "&destinationAccount=" + this.accountCredit,
            )
                .then(response => {
                    swal({
                        title: "Transfer!",
                        icon: "success",
                    });
                    location.reload()
                })
                .then(response => window.location.href = "/web/webPages/accounts.html")
                .catch(err => swal("Error to realize the transaction, please verify the amount"))
        },
        filtrarCuentas() {
            this.accountsFiltradas = this.accounts.filter(e => e.number != this.accountDebit)

        },
        logout(){
            axios.post('/api/logout')
            .then(response => window.location.replace("/web/index.html"))
            .catch(e =>{
                console.log(e);
            });
        }
    },
})
app.mount("#app")
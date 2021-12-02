const app = Vue.createApp({
    data() {
        return {
            account: [],
            transactions: [],
        }
    }, created() {
        // const urlParams = new URLSearchParams(window.location.search);
        // const myParam = urlParams.get('id');
        // axios.get(`/api/accounts/${myParam}`)
        // .then((res)=>{
        //     this.account = res.data;
        //     this.transactions = res.data.transactions;
        //     this.transactions.sort((a,b) => a.amount > b.amount ? 1 : -1)
        //     })
        //     .catch(err => console.error(err.message))
        const urlSearchParams = new URLSearchParams(window.location.search);
        const params = Object.fromEntries(urlSearchParams.entries());


        console.log(params.id)
        axios.get("/api/accounts/" + params.id)
            .then(response => {
                console.log(response)
                this.account = response.data
                this.transactions = response.data.transactions
                this.transactions.sort((a, b) => b.id - a.id)
                console.log(this.transactions)
            })
            .catch(error => {
                alert(error)
            })

        axios.get("/api/clients/current")
            .then(response => {
                console.log(response)
                this.clientes = response.data
                this.name = this.clientes.firstName
                this.lname = this.clientes.lastName

                console.log(this.accounts)
            })
            .catch(error => {
                res.data
            })
    },
    methods: {
        formatDate(date){
            return new Date(date).toLocaleDateString('en-gb');
        },
        logout(){
            axios.post('/api/logout')
            .then(response => window.location.replace("/web/index.html"))
            .catch(e =>{
                console.log(e);
            });
        },
        
    }
})
app.mount("#app")

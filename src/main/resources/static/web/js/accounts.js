const app = Vue.createApp({
    data() {
        return {
            clients: [],
            accounts:[],
            loans:[]
        }
    }, created() {
        this.loadData();
        this.accounts.sort((a,b) => a.id > b.id ? 1 : -1);

    },
    methods: {
        loadData() {
            axios.get('/api/clients/current')
                .then(resp => {
                    this.clients = resp.data
                    this.accounts = resp.data.accounts
                    this.accounts.sort((a,b) => a.number>b.number ? 1 : -1)
                    this.loans = resp.data.clientLoans
                })
        },
        AddAccount(accType) {
            axios.post('/api/clients/current/accounts', "accType=" + accType,
                {
                    headers: { 'content-type': 'application/x-www-form-urlencoded' }
                })
            location.reload()
        },
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
        deleteAccount(account) {
            if (account.balance != 0) {
                this.isDeleteAcc = true
                this.deletingAccount = account
                return
            }
            swal({
                title: "Are you sure?",
                text: "Once deleted, you will not be able to recover this Account!",
                icon: "warning",
                buttons: true,
                dangerMode: true,
            })
                .then((willDelete) => {
                    if (willDelete) {
                        axios.delete('/api/clients/current/accounts/' + account.number, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                            .then(() => swal("The Account has been deleted", {
                                icon: "success",
                            }))
                            .then(() => location.reload())
                            .catch(err => console.log(err))
                    }
                });
        },
        seeTransactions(account){
            window.location.href = "account.html?id=" + account.id;
        },
    }
})
app.mount("#app")
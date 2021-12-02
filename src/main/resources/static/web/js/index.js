const app = Vue.createApp({
    data() {
        return {
            form: {
                email: '',
                pwd: '',
                fName: '',
                lName: '',
            }
        }
    }, created() {
    },
    methods: {
        login() {
            axios.post('/api/login',`email=${this.email}&password=${this.pwd}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response => window.location.href = "/web/webPages/accounts.html")
            .catch(e =>{
                swal("¡Password or Email wrong!")
            })
        },
        completarRegistro(){
            axios.post('/api/clients',"firstName="+ this.fName + "&lastName=" + this.lName + "&email=" + this.email + "&password=" + this.pwd,
            {headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response => {
                axios.post('/api/login', 'email=' + this.email + '&password=' + this.pwd,
                {headers:{'content-type':'application/x-www-form-urlencoded'}})
            })
            .then(response => {
                swal("Login completed!")
                location.reload
                window.location.href="/web/index.html"
            })
            .catch(e => {
                swal("¡Password or Email Wrong!");
            })
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

const { createApp } = Vue

createApp({
    data(){
        return {
            url : "/api/clients/current",
            firstName: "",
            lastName: "",
            password: "",
            email: "",
            errorRegister: false,
            res :{}
        }
        
    },
    created(){
        this.loadData()

    },
    mounted(){

    },
    methods:{
        loadData(){
            axios.get(this.url)
            .then((respuesta) =>{
                this.res = respuesta;
                this.json = respuesta
                this.client = respuesta.data
                this.cards = this.client.cardsDTO
            } )
            .catch( exception => console.log(exception)) 
        },

        signIn(){
            axios.post('/api/login',`email=${this.email}&password=${this.password}`)
            .then(response =>{
                if(this.email == "admin@gmail.com"){
                    window.location.href = "/manager.html"
                }else{
                    window.location.href = "/web/products.html"
                }
            })
            .catch((exception)=>  console.log(exception))
        },
        register(){
            axios.post('/api/clients',`firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`)
            .then(response => this.signIn())
            .catch((error)=> this.errorRegister = true)
        },
    },
    computed:{
        
    }
}).mount('#app')  
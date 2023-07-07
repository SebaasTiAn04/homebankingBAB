const { createApp } = Vue

createApp({
    data(){
        return {
            url : "/api/clients/current",
            password: "",
            email: "",
            errorLogin: false
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
                this.json = respuesta
                this.client = respuesta.data
                this.cards = this.client.cardsDTO
            } )
            .catch( exception => console.log(exception)) 
        },

        LogIn(){
            axios.post('/api/login',`email=${this.email}&password=${this.password}`)
            .then((response) =>{
                if(this.email == "admin@gmail.com"){
                    window.location.href = "/manager.html"
                }else{
                    window.location.href = "/web/products.html"
                }
                
            })
            .catch((exception) =>{
                if(this.email == ""){
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'Your email is empty!',
                    })
                }else if(this.password == ""){
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'Your password is empty!',
                    })
                }else{
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'An error occurred',
                    })
                    
                }
               /*  this.errorLogin = true */
            } )
        },
    },
    computed:{
        
    }
}).mount('#app')  
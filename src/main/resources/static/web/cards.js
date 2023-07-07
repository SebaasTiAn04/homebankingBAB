const { createApp } = Vue

createApp({
    data(){
        return {
            url : "/api/clients/current",
            client: {},
            json : [],
            cards: [],
            cardsActive:[],
            cardsDebit:[],
            cardsCredit:[],
            inputFiltro: "",
            errorLogin: false,
            CardMax: false,
            cardType: [],
            type: "EMPTY",
            color: "EMPTY",
            cardNumberDelete: "",
            date: new Date(),
            fecha : {},
            fechaString: "",
        }
    },
    created(){
        this.loadData()
        this.getClient()
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
                this.backupCards =  this.client.cardsDTO
                this.CardMax = this.cards.length == 6

                this.fecha = [
                    year =this.date.getFullYear(),
                    month = this.date.getMonth() + 1,
                    date =this.date.getDate(),
                ];
                this.fechaString = this.fecha.toString().replace(',', '-');
                this.fechaString = this.fechaString.replace(',', '-');
                
            })
            .catch( exception => console.log(exception)) 
        },

        getClient(){
            axios.get("/api/clients/current")
            .then(res => this.client = res.data)
            .catch(exception => console.log(exception))
        },
        logOut(){
            return axios.post('/api/logout')
            .then(response=> window.location.href = "/web/index.html")
        },
        addCard(){
            axios.post('/api/clients/current/cards',`type=${this.type}&color=${this.color}`)
            .then(response =>{
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'Your work has been saved',
                    showConfirmButton: false,
                    timer: 15000
                  })
                  window.location.href = "/web/cards.html"
            })
            .catch((exception)=>{
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'Sorry, ' +exception.response.data,
                    })
            } )
        },
        deleteCard(){
            axios.patch('/api/clients/current/cards/delete',`number=${this.cardNumberDelete}`)
            .then(response => window.location.href = "/web/cards.html")
            .catch((exception)=>  console.log(exception))
        },
    },
    computed:{
        filterCards(){
            this.cardsCredit = this.cardsActive.filter(card => card.type === "CREDIT")
            this.cardsDebit = this.cardsActive.filter(card => card.type === "DEBIT") 
        },
        filterCardsActive(){
            this.cardsActive = this.cards.filter(card => card.active === true)
        }
    }
}).mount('#app')  
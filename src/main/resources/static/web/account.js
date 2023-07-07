const { createApp } = Vue

createApp({
    data(){
        return {
            json : [],
            account: {},
            accountDTO: [],
            transactions: [],
            transactionsBackup: [],
            client: {},
            dateStart: "",
            dateEnd: "",
            transactionFilterDate: [],
        }
        
    },
    created(){
        this.loadData(),
        this.getClient()
    },
    mounted(){

    },
    methods:{
        loadData(){
            const queryString = location.search;
            const params = new URLSearchParams(queryString);
            const id = params.get("id");

            axios.get( "/api/accounts/" + id)
            .then((respuesta) =>{
                this.json = respuesta
                this.account = respuesta.data
            } )
            .then(res => {
                this.transactions = this.account.transactionDTO
                this.transactionsBackup =this.account.transactionDTO
            } )
            .catch( exception => console.log(exception)) 
        },
        precioFormat(number){
            USDollar = new Intl.NumberFormat('en-US', {
              style: 'currency',
              currency: 'USD',
          });
          return USDollar.format(number)
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
        dowloandPdf(){
            let TransactionDateDTO = {
                number: this.account.number,
                yearStart: parseInt(this.dateStart.substring(0, 4)),
                monthStart: parseInt(this.dateStart.substring(5, 7)),
                dayStart: parseInt(this.dateStart.substring(8, 10)),

                yearEnd: parseInt(this.dateEnd.substring(0, 4)),
                monthEnd: parseInt(this.dateEnd.substring(5, 7)),
                dayEnd: parseInt(this.dateEnd.substring(8, 10)),
            }
            console.log(TransactionDateDTO)
            axios({
                url: '/api/pdf/generate',
                method: 'POST',
                data: TransactionDateDTO,
                responseType: 'blob'
            }).then((response) => {
                const href = URL.createObjectURL(response.data)
                
                const link = document.createElement('a')
                link.href = href
                link.setAttribute('download', 'pdf_.pdf')
                document.body.appendChild(link)
                link.click()
            }) .catch(response => console.log(response))

        }
    },
    computed:{
             filterTransaction(){
                 if(this.dateStart != "" && this.dateEnd != ""){
                     this.transactionFilterDate = this.transactions.filter(transaction => transaction.date.substring(2,10)  <= this.dateEnd.substring(2) &&
                                                                           transaction.date.substring(2,10) >= this.dateStart.substring(2))
                 }
                 if(this.dateStart == "" && this.dateEnd == ""){
                     this.transactionFilterDate = this.transactionsBackup
                 }
             }
         }
}).mount('#app')  




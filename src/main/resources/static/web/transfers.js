const { createApp } = Vue

let app = createApp({
    data() {
        return{
            
            url : "/api/clients/current",
            clientes : [],
            accounts: [],
            accountsExcludingO: [],
            radioTransfer: 'Own',
            selectAccount:null,
            selectAccountTransferTo: '',
            amount: '',
            description: '',
            accountThirdD: '',



        }
    },
    mounted(){
        this.loadData()
    },
    methods:{
        loadData() {
            axios.get(this.url)
            .then((respuesta) => {
                this.clientes = respuesta.data
                this.accounts = this.clientes.accountsDTO

            })
            .catch((error) =>  console.log(error))
        },
        sendTransfer(){
            axios.post('/api/transactions',`amount=${this.amount}&description=${this.description}&accountO=${this.selectAccount}&accountD=${this.accountThirdD}`)
            .then((response) =>  {Swal.fire({
                icon: 'success',
                text: 'The transaction was a success',
               
              })
              window.location.href = "/web/products.html"
            }).catch((exception) => {Swal.fire({
                icon: 'error',
                text: 'Sorry, ' +exception.response.data,
              })
              console.log(exception)  
            })
        },

        alert(){
            Swal.fire({
                title: 'Are you sure you want to make this transaction?',
                text: "You won't be able to revert this!",
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes'
              }).then((result) => {
                if (result.isConfirmed) {
                    this.sendTransfer()
                }
              })
        }
      

    },
    computed:{
        selectedAccount(){
            this.accountsExcludingO = this.accounts.filter(account => account.number != this.selectAccount)
          },
          filterAccountActive(){
            this.accountsActive = this.accounts.filter(account => account.active === true)
        }
    },
    
}).mount('#app');
const { createApp } = Vue

let app = createApp({
    data() {
        return{ 
            url : "/api/clients/current",
            urlLoans : "/api/loans",
            client: {},
            clientes : [],
            accounts : [],
            accountsActive:[],
            loans: [],
            loanSelected: [],
            paymentSelected: null,
            selectLoanId: null,
            selectAccount: null,
            selectPayment: 'Select Payment',
            selectPaymentDefault: 'Select Payment',
            amountPorcentaje: null,
            eachPayment: 0,
            amount: '',
            totalAmount: '',
        }
    },
    
    mounted(){
        this.loadDataLoan()
        this.loadData()
    },
    methods:{
        loadData() {
            axios.get(this.url)
            .then((data) => {
                this.clientes = data.data
                this.client = data.data
                this.accounts = this.clientes.accountsDTO
            })
            .catch((exception) => console.log(exception))
        },
        precioFormat(number){
            USDollar = new Intl.NumberFormat('en-US', {
              style: 'currency',
              currency: 'USD',
          });
          return USDollar.format(number)
        },
        logOut(){
            axios.post('/api/logout')
            .then(response=> window.location.href = "/web/index.html")
        },
        loadDataLoan(){
            axios.get(this.urlLoans)
            .then((res) => {
                this.loans = res.data
                this.exception = res.status    
                this.loanSelected = this.loans
            })
            .catch((exception) => console.log(exception))
        },
        applyLoan(){
            this.paymentSelected = this.selectPayment;
            axios.post('/api/loans',{
                "idLoan": this.selectLoanId,
                "amount": this.amount,
                "payment": this.paymentSelected,
                "accountDestiny": this.selectAccount
            })
            .then(response => {
                Swal.fire({
                    icon: 'success',     
                    text: 'The transaction was a success',
                })
                    this.paymentSelected = null;  
                    this.loadData()
            })
            .catch((exception)=> {Swal.fire({
                icon: 'error',  
                text: 'Sorry, ' +exception.response.data,
              })
              console.log(exception)
            }
            
            )
        },
        surePopUp(){
            Swal.fire({
                title: 'Are you sure you want to apply for this loan?',
                text: `Each payment will be ${this.precioFormat(this.eachPayment) }.`,
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes'
              })
              .then((result) => {
                if (result.isConfirmed) {
                    this.applyLoan()
                }
              })
        } 
    },
    computed:{
        selectLoan(){
            this.loanSelected = this.loans.filter(loan => loan.id == this.selectLoanId)
        },
        filterAccountActive(){
            this.accountsActive = this.accounts.filter(account => account.active === true)
            if(this.loanSelected.length != 0){
                this.totalAmount = this.amount * this.loanSelected[0].percentageInterest
                
            }
           
        } ,
        paymentSummary(){    
            this.eachPayment = parseInt(this.totalAmount) / parseInt(this.selectPayment)
        }
  
    },
}).mount('#app');
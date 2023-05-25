<template>
    <body class="bodyStructure">
        <div class="structure">
            <div class="headInfo">
                <div class="accountNumber">
                    <p>rekeningnummer....</p>
                </div>
                <div class="groupOptions">
                    <div class="option">
                        <button>
                            Deposit
                        </button>
                    </div>
                    <div class="option">
                        <button>
                            Withdraw
                        </button>
                    </div>
                    <div class="option">
                        <button>
                            Transaction
                        </button>
                    </div>
                </div>
            </div>
            <div class="bodyInfo">
                <!-- Hier komen de transacties -->

                <div v-for="index in 10" :key="index" class="transaction">
                    <h1>blabla</h1>
                </div> 
                   
            </div>
        </div>
    </body>
</template>

<script>
</script>

<style>
@import '../../assets/css/customerTransaction.css';
</style>
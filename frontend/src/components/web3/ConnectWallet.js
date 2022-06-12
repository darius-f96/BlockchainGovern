import Web3 from "web3"

let selectedAccount

export const ConnectWallet = () => {
    let provider = window.ethereum

    if (typeof provider !== 'undefined'){

        provider.request({
                method: 'eth_requestaccounts'
            }).then( (accounts)=>{
                selectedAccount = accounts[0]
                console.log(`Selected account is ${selectedAccount}`)
            }).catch ((error)=>{
                console.log(error)
            })
        provider.on('accountsChanged', (accounts)=>{
            selectedAccount = accounts[0]
            console.log(`Newly selected account is ${selectedAccount}`)})
    }

    const web3 = new Web3(provider)
}
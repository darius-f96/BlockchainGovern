import { metamask, web3 } from "../../utils/provider"

export const ConnectWallet = async () => {
    if (!web3 ){
        await web3.eth.enable()
    }
    const accounts = await web3.eth.requestAccounts()
    if (accounts)
        return accounts[0].toLowerCase()
    else
        return ""
        
}
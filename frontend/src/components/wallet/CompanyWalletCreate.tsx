import { Button, TextField } from "@mui/material"
import { BigNumber, ethers, Wallet } from "ethers"
import React, { useEffect, useState } from "react"
import { Form } from "react-bootstrap"
import SpringBootRequest from "../../services/SpringBootRequest"
import { CompanyWallet } from "../../utils/definitions"
import { metamask, provider, web3 } from "../../utils/provider"
import { switchDisplay } from "../../utils/switchDisplay"
import { ConnectWallet } from "../web3/ConnectWallet"

export const CompanyWalletCreate = (props: {id:string} ) =>{
    const [walletCode, setWalletCode] = useState<string>('')
    const [description, setDescription] = useState<string>('')
    const [balance, setBalance] = useState<String>('')
    const [divDisplay, setDivDisplay] = useState<string>('none')

    const handleChangeWalletCode = (e:React.ChangeEvent<HTMLInputElement>) => {
        setWalletCode(e.currentTarget.value)
    }
    const handleChangeDescription = (e:React.ChangeEvent<HTMLInputElement>) => {
        setDescription(e.currentTarget.value)
    }

    const saveData = async (e:any) => {
        e.preventDefault()
        const selectedAccount = await ConnectWallet()
        const payload:CompanyWallet ={

            companyId : props.id,
            id : "",
            walletCode : walletCode,
            walletDescription : description,
            walletId: selectedAccount
        }
        SpringBootRequest(`companyWallet`, "POST", payload).then(response=>{
        //     window.location.reload();
            console.log(response)
        })
        
       setDivDisplay('none')
       
    }

    return(
        <div style={{marginTop:25}}>
            <Button variant="contained" onClick={()=>{ setDivDisplay(switchDisplay(divDisplay)) }}>Link wallet to account</Button>
            <div style ={{display:divDisplay}}>
                <Form onSubmit={saveData}>
                    <TextField onChange={handleChangeWalletCode} id="outlined-basic" label="Wallet Code" defaultValue={walletCode} variant='outlined' />
                    <TextField onChange={handleChangeDescription} id="outlined-basic" label="Description" variant='outlined' defaultValue={description} />
                    <br></br><Button type="submit" variant="contained" style={{marginTop:10}}>Save changes</Button>
                </Form>
            </div>
        </div>
    )
}
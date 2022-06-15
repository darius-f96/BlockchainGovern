import { Button, TextField } from "@mui/material"
import React, { useState } from "react"
import { BooleanField, NumberField, NumberInput } from "react-admin"
import { Form } from "react-bootstrap"
import SpringBootRequest from "../../services/SpringBootRequest"
import { B2BContract, ContractDetails } from "../../utils/definitions"
import { switchDisplay } from "../../utils/switchDisplay"

export const B2BContractCreate = (props: {cui:string} ) =>{
    const [contractCode, setContractCode] = useState<string>('')
    const [description, setDescription] = useState<string>('')
    const [companyid2, setCompanyid2] = useState<string>('')
    const [terms, setTerms] = useState<string>('')
    const [divDisplay, setDivDisplay] = useState<string>('none')
    const [wireFrequency, setWireFrequency] = useState<number>(0)
    const [amount, setAmount] = useState<number>(0)
    const [daysBeforeCancel, setDaysBeforeCancel] = useState<number>(0)

    const handleChangeContractCode = (e:React.ChangeEvent<HTMLInputElement>) => {
        setContractCode(e.currentTarget.value)
    }
    const handleChangeDescription = (e:React.ChangeEvent<HTMLInputElement>) => {
        setDescription(e.currentTarget.value)
    }
    const handleChangeCompanyid2 = (e:React.ChangeEvent<HTMLInputElement>) => {
        setCompanyid2(e.currentTarget.value)
    }
    const handleChangeTerms = (e:React.ChangeEvent<HTMLInputElement>) => {
        setTerms(e.currentTarget.value)
    }
    const handleChangeWireFrequency = (e:React.ChangeEvent<HTMLInputElement>) => {
        setWireFrequency(parseInt(e.currentTarget.value))
    }
    const handleChangeAmount = (e:React.ChangeEvent<HTMLInputElement>) => {
        setAmount(parseInt(e.currentTarget.value))
    }
    const handleChangeDaysBeforeCancel = (e:React.ChangeEvent<HTMLInputElement>) => {
        setDaysBeforeCancel(parseInt(e.currentTarget.value))
    }

    const saveData = () => {
        const contractDetails:ContractDetails = {
            wireFrequency : wireFrequency,
            active : false,
            amount : amount,
            lastWire : null,
            startDate : null,
            daysBeforeCancel : daysBeforeCancel,
            id : ''
        }
        const payload:B2BContract ={
            accepted : false,
            companyId1 : props.cui,
            companyId2 : companyid2,
            terms : terms,
            id : '',
            contractCode : contractCode,
            description : description,
            contractDetails: contractDetails
        }
        SpringBootRequest(`companyContractCompany/`, "POST", payload).then(response=>{
        //     window.location.reload();
            console.log(response)
        })
       setDivDisplay('none')
    }

    return(
        <div style={{marginTop:25}}>
            <Button variant="contained" onClick={()=>{ setDivDisplay(switchDisplay(divDisplay)) }}>Create B2B contract</Button>
            <div style ={{display:divDisplay}}>
                <Form onSubmit={saveData}>
                    <TextField onChange={handleChangeContractCode} id="outlined-basic" label="Contract Code" variant='outlined' />
                    <TextField disabled id="outlined-basic" label="CUI" variant='outlined' defaultValue={props.cui}/>
                    <TextField onChange={handleChangeCompanyid2} id="outlined-basic" label="Contractor CUI" variant='outlined' />
                    <TextField onChange={handleChangeTerms} id="outlined-basic" label="Terms" variant='outlined' /><br/>
                    <TextField onChange={handleChangeDescription} id="outlined-basic" label="Description" variant='outlined' />
                    <TextField onChange={handleChangeAmount} id="outlined-basic" label="Amount" variant='outlined'
                    onKeyDown={(event) => {
                        if (!/[0-9]/.test(event.key) && !/Backspace/.test(event.key)) {
                        event.preventDefault();
                        }
                    }} />
                    <TextField onChange={handleChangeDaysBeforeCancel} id="outlined-basic" label="Days before cancel" variant='outlined'
                    onKeyDown={(event) => {
                        if (!/[0-9]/.test(event.key) && !/Backspace/.test(event.key)) {
                        event.preventDefault();
                        }
                    }} />
                    <TextField onChange={handleChangeWireFrequency} id="outlined-basic" label="Wire frequency" variant='outlined'
                    onKeyDown={(event) => {
                        if (!/[0-9]/.test(event.key) && !/Backspace/.test(event.key)) {
                        event.preventDefault();
                        }
                    }} />
                    <br></br><Button type="submit" variant="contained" style={{marginTop:10}}>Save changes</Button>
                </Form>
            </div>
        </div>
    )
}
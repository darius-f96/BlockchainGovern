import { Button, TextField } from "@mui/material"
import React, { useState } from "react"
import { useRecordContext } from "react-admin"
import { Form } from "react-bootstrap"
import SpringBootRequest from "../../services/SpringBootRequest"
import { B2BContract, B2PContract } from "../../utils/definitions"
import { switchDisplay } from "../../utils/switchDisplay"

export const B2PContractCreate = ( ) =>{
    const record = useRecordContext()
    const [contractCode, setContractCode] = useState<string>('')
    const [description, setDescription] = useState<string>('')
    const [appUserId, setAppUserId] = useState<string>('')
    const [terms, setTerms] = useState<string>('')
    const [divDisplay, setDivDisplay] = useState<string>('none')
    const accepted = false;

    const handleChangeContractCode = (e:React.ChangeEvent<HTMLInputElement>) => {
        setContractCode(e.currentTarget.value)
    }
    const handleChangeDescription = (e:React.ChangeEvent<HTMLInputElement>) => {
        setDescription(e.currentTarget.value)
    }
    const handleChangeAppUserId = (e:React.ChangeEvent<HTMLInputElement>) => {
        setAppUserId(e.currentTarget.value)
    }
    const handleChangeTerms = (e:React.ChangeEvent<HTMLInputElement>) => {
        setTerms(e.currentTarget.value)
    }

    const saveData = () => {
        const payload:B2PContract ={
            accepted : accepted,
            companyId : record.cui,
            appUserId : appUserId,
            terms : terms,
            id : '',
            contractCode : contractCode,
            description : description
        }
        SpringBootRequest(`companyContractPerson/`, "POST", payload).then(response=>{
        //     window.location.reload();
            console.log(response)
        })
       setDivDisplay('none')
    }

    return(
        <div>
            <Button variant="contained" onClick={()=>{ setDivDisplay(switchDisplay(divDisplay)) }}>Create B2B contract</Button>
            <div style ={{display:divDisplay}}>
                <Form onSubmit={saveData}>
                    <TextField onChange={handleChangeContractCode} id="outlined-basic" label="Contract Code" variant='outlined' />
                    <TextField disabled id="outlined-basic" label="CUI" variant='outlined' defaultValue={record.cui}/>
                    <TextField onChange={handleChangeAppUserId} id="outlined-basic" label="App User Id" variant='outlined' />
                    <TextField onChange={handleChangeTerms} id="outlined-basic" label="Terms" variant='outlined' />
                    <TextField disabled id="outlined-basic" label="Accepted" variant='outlined' defaultValue={accepted}/>
                    <TextField onChange={handleChangeDescription} id="outlined-basic" label="Description" variant='outlined' /><br/>
                    <Button type="submit" variant="contained" style={{marginTop:10}}>Save changes</Button>
                </Form>
            </div>
        </div>
    )
}
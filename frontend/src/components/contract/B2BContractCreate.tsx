import { Button, TextField } from "@mui/material"
import React, { useState } from "react"
import { useRecordContext } from "react-admin"
import { Form } from "react-bootstrap"
import SpringBootRequest from "../../services/SpringBootRequest"
import { B2BContract } from "../../utils/definitions"
import { switchDisplay } from "../../utils/switchDisplay"

export const B2BContractCreate = ( ) =>{
    const record = useRecordContext()
    const [contractCode, setContractCode] = useState<string>('')
    const [description, setDescription] = useState<string>('')
    const [companyid2, setCompanyid2] = useState<string>('')
    const [terms, setTerms] = useState<string>('')
    const [divDisplay, setDivDisplay] = useState<string>('none')
    const accepted = false;

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

    const saveData = () => {
        const payload:B2BContract ={
            accepted : accepted,
            companyId1 : record.cui,
            companyId2 : companyid2,
            terms : terms,
            id : '',
            contractCode : contractCode,
            description : description
        }
        SpringBootRequest(`companyContractCompany/`, "POST", payload).then(response=>{
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
                    <TextField onChange={handleChangeCompanyid2} id="outlined-basic" label="Contractor CUI" variant='outlined' />
                    <TextField onChange={handleChangeTerms} id="outlined-basic" label="Terms" variant='outlined' />
                    <TextField disabled id="outlined-basic" label="Accepted" variant='outlined' defaultValue={accepted}/>
                    <TextField onChange={handleChangeDescription} id="outlined-basic" label="Description" variant='outlined' /><br/>
                    <Button type="submit" variant="contained" style={{marginTop:10}}>Save changes</Button>
                </Form>
            </div>
        </div>
    )
}
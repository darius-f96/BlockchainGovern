import { Button, TextField } from "@mui/material"
import React, { useState } from "react"
import { useRecordContext } from "react-admin"
import { Form } from "react-bootstrap"

export const B2BContractCreate = () =>{
    const record = useRecordContext()
    const [contractCode, setContractCode] = useState<string>()
    const [description, setDescription] = useState<string>()
    const [companyid1, setCompanyid1] = useState<string>()
    const [companyid2, setCompanyid2] = useState<string>()
    const [visible, setVisible] = useState<boolean>(true)

    const handleChangeContractCode = (e:React.ChangeEvent<HTMLInputElement>) => {
        setContractCode(e.currentTarget.value)
    }
    const handleChangeDescription = (e:React.ChangeEvent<HTMLInputElement>) => {
        setDescription(e.currentTarget.value)
    }
    const handleChangeCompanyid1 = (e:React.ChangeEvent<HTMLInputElement>) => {
        setCompanyid1(e.currentTarget.value)
    }
    const handleChangeCompanyid2 = (e:React.ChangeEvent<HTMLInputElement>) => {
        setCompanyid2(e.currentTarget.value)
    }
    const saveData = () => {
        setVisible(false)
    }

    return(
        () => {
            if (visible){
                return <div></div>
            }
            else return(
            <div>
            <Form onSubmit={saveData}>
                <TextField onChange={handleChangeContractCode} id="outlined-basic" label="Contract Code" variant='outlined' />
                <TextField onChange={handleChangeCompanyid1} id="outlined-basic" label="CUI" variant='outlined' />
                <TextField onChange={handleChangeCompanyid2} id="outlined-basic" label="Contractor CUI" variant='outlined' />
                <TextField onChange={handleChangeDescription} id="outlined-basic" label="Description" variant='outlined' /><br/>
                <Button type="submit" variant="contained" style={{marginTop:10}}>Save changes</Button>
            </Form>
        </div>)
        }
    )
}
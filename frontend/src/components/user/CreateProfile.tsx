import { Button, FormControl, InputLabel, MenuItem, Select, SelectChangeEvent, TextField } from "@mui/material"
import { DesktopDatePicker, LocalizationProvider } from "@mui/x-date-pickers"
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns"
import React from "react"
import { Form } from "react-admin"
import { Navigate } from "react-router-dom"
import SpringBootRequest from "../../services/SpringBootRequest"
import { PersonEntity } from "../../utils/definitions"


export const CreateProfile = () => {
    const [value, setValue] = React.useState<Date | null>(null)
    const [salutation, setSalutation] = React.useState<string>('')
    const [gender, setGender] = React.useState<string>('')
    const [firstname, setFirstname] = React.useState<string>('')
    const [lastname, setLastname] = React.useState<string>('')
    const [personCode, setPersonCode] = React.useState<string>('')
      
    const handleChange = (newValue: Date | null) => {
        setValue(newValue);
    };

    const handleChangeGender = (event: SelectChangeEvent) => {
        setGender(event.target.value)
    };

    const handleChangeSalutation = (e : React.ChangeEvent<HTMLInputElement>) =>{
        setSalutation(e.currentTarget.value)
    }

    const handleChangeFirstname = (e : React.ChangeEvent<HTMLInputElement>) =>{
        setFirstname(e.currentTarget.value)
    }
    const handleChangeLastname = (e : React.ChangeEvent<HTMLInputElement>) =>{
        setLastname(e.currentTarget.value)
    }
    const handleChangePersonCode = (e : React.ChangeEvent<HTMLInputElement>) =>{
        setPersonCode(e.currentTarget.value)
    }

    const saveData = () => {
        let rec : PersonEntity = {
            AppUserId : '',
            birthDate : null,
            firstname : '',
            gender : '',
            salutation : '',
            id : '',
            companyId : '',
            lastname : '',
            personCode: ''
        }
        rec.birthDate = value
        rec.lastname = lastname
        rec.firstname = firstname
        rec.gender = gender
        rec.salutation = salutation
        rec.personCode = personCode
        SpringBootRequest(`person/`, "POST", rec).then(response=>{
            window.location.reload();
        })
     }
    return(
        <>
        <div style={{marginTop:40}}>
            <Form onSubmit={saveData}>  
                <TextField onChange={handleChangeSalutation} id="outlined-basic" label="Salutation" variant='outlined' /><br/>
                <TextField onChange={handleChangeFirstname} id="outlined-basic" label="Firstname" variant='outlined' /><br/>
                <TextField onChange={handleChangeLastname} id="outlined-basic" label="Lastname" variant='outlined' /><br/>
                <TextField onChange={handleChangePersonCode} id="outlined-basic" label="Person Code" variant='outlined'/><br/>
                <FormControl sx={{ m: 1, minWidth: 120 }}>
                <InputLabel>Gender</InputLabel>
                <Select
                value={gender}
                label="Gender"
                onChange={handleChangeGender}
                style={{width:220, backgroundColor:"white"}}
                >
                <MenuItem value={'Male'}>Male</MenuItem>
                <MenuItem value={'Female'}>Female</MenuItem>
                <MenuItem value={'Other'}>Other</MenuItem>
                </Select>            
                </FormControl><br/>
                <LocalizationProvider dateAdapter={AdapterDateFns}>
                    <DesktopDatePicker
                    label="Birth Date"
                    inputFormat="dd//MM//yyyy"
                    value={value}
                    onChange={handleChange }
                    renderInput={(params:any) => <TextField {...params} />}
                    />
                 </LocalizationProvider><br></br>
                 <Button type="submit" variant="contained" style={{marginTop:10}}>Save changes</Button>

            </Form>
        </div>
        </>
    )
}
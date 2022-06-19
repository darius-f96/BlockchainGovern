import { Button, FormControl, InputLabel, MenuItem, Select, SelectChangeEvent, TextField } from "@mui/material"
import { DesktopDatePicker } from '@mui/x-date-pickers/DesktopDatePicker';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import React from "react"
import { Form } from "react-admin"
import { PersonEntity } from "../../utils/definitions"
import { LocalizationProvider } from "@mui/x-date-pickers";
import SpringBootRequest from "../../services/SpringBootRequest";

export const DeployContractForm = (props: {rec: PersonEntity }) => {
    const [value, setValue] = React.useState<Date | null>(props.rec.birthDate)
    const [salutation, setSalutation] = React.useState<string>(props.rec.salutation)
    const [gender, setGender] = React.useState<string>(props.rec.gender)
    const [firstname, setFirstname] = React.useState<string>(props.rec.firstname)
    const [lastname, setLastname] = React.useState<string>(props.rec.lastname)
    const [personCode, setPersonCode] = React.useState<string>(props.rec.personCode)
      
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
        props.rec.birthDate = value
        props.rec.lastname = lastname
        props.rec.firstname = firstname
        props.rec.gender = gender
        props.rec.salutation = salutation
        props.rec.personCode = personCode
        SpringBootRequest(`person/${props.rec.id}`, "PUT", props.rec)
     }
    return(
        <div style={{marginTop:40}}>
            <Form onSubmit={saveData}>  
                <TextField onChange={handleChangeSalutation} id="outlined-basic" label="Salutation" variant='outlined' defaultValue={props.rec.salutation}/><br/>
                <TextField onChange={handleChangeFirstname} id="outlined-basic" label="Firstname" variant='outlined' defaultValue={props.rec.firstname}/><br/>
                <TextField onChange={handleChangeLastname} id="outlined-basic" label="Lastname" variant='outlined' defaultValue={props.rec.lastname}/><br/>
                <TextField onChange={handleChangePersonCode} id="outlined-basic" label="Person Code" variant='outlined' defaultValue={props.rec.personCode}/><br/>
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
    )
}
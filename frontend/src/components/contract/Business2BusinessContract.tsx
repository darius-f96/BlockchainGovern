import { TextField } from "@mui/material";
import { RecordContext, RichTextField, useRecordContext } from "react-admin";
import { B2BContract } from "../../utils/definitions";


export const Business2BusinessContract = (props: {contract:B2BContract }) =>{
    const style = {
        width: 650
    }
    if (props.contract !== null){
        return (
            <div>
                <TextField disabled defaultValue={props.contract.contractCode}></TextField> between:
                <TextField disabled defaultValue={props.contract.companyId1}></TextField> and 
                <TextField disabled defaultValue={props.contract.companyId2}></TextField> description:
                <TextField style={style} disabled defaultValue={props.contract.description}></TextField> <br></br>
                Terms:<TextField style={style} disabled defaultValue={props.contract.terms}/> <br></br>
                Accepted:<TextField disabled defaultValue={props.contract.accepted}></TextField>
            </div>
        )
    }
    else return <div>No contracts</div>
    
    
}
import { TextField } from "@mui/material";
import { useRecordContext } from "react-admin";
import { B2PContract } from "../../utils/definitions";


export const Business2PersonContract = (props: {contract:B2PContract }) =>{
    const style = {
        width: 650
    }
    if (props.contract !== null){
        return (
            <div>
                <TextField disabled defaultValue={props.contract.contractCode}></TextField> between:
                <TextField disabled defaultValue={props.contract.companyId}></TextField> and 
                <TextField disabled defaultValue={props.contract.appUserId}></TextField> description:
                <TextField style={style} disabled defaultValue={props.contract.description}></TextField> <br></br>
                Terms:<TextField style={style} disabled defaultValue={props.contract.terms}/> <br></br>
                Accepted:<TextField disabled defaultValue={props.contract.accepted}></TextField>
            </div>
        
        )
    }
    else return <div>No contracts</div>
}
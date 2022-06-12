import { TextField } from "@mui/material";
import { B2PContract } from "../../utils/definitions";


export const Business2PersonContract = (props: {contract:B2PContract }) =>{

    return (
        <div>
            <TextField>{props.contract.contractCode}</TextField> between:
            <TextField>{props.contract.companyid}</TextField>
            <TextField>{props.contract.personid}</TextField> description:
            <TextField>{props.contract.description}</TextField>
        </div>
    )
}
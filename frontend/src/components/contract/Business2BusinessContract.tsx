import { TextField } from "@mui/material";
import { B2BContract } from "../../utils/definitions";


export const Business2BusinessContract = (props: {contract:B2BContract }) =>{

    return (
        <div>
            <TextField>{props.contract.contractCode}</TextField> between:
            <TextField>{props.contract.companyid1}</TextField>
            <TextField>{props.contract.companyid2}</TextField> description:
            <TextField>{props.contract.description}</TextField>
        </div>
    )
}
import { Button, Typography } from "@mui/material";
import React from "react";
import { useRecordContext } from "react-admin";
import { B2BContract } from "../../utils/definitions";
import { B2BContractCreate } from "../contract/B2BContractCreate";
import { B2PContractCreate } from "../contract/B2PContractCreate";
import { Business2BusinessContract } from "../contract/Business2BusinessContract";
import { Business2PersonContract } from "../contract/Business2PersonContract";

export const CompanyDetails = () => {
    const data = useRecordContext();
    const [displayB2BCreate, setDisplayB2BCreate] = React.useState<boolean>(false)
    const [displayB2PCreate, setDisplayB2PCreate] = React.useState<boolean>(false)
    const [displayWalletCreate, setDisplayWalletCreate] = React.useState<boolean>(false)

    console.log(data)
    return (
        <div style={{ width: "100%", margin: '1em' }}>
            <div>
                <Typography variant="h6">Wallets</Typography>
                <Typography variant="body2">
                    Walletid: {data.id}
                </Typography>
            </div>
            <div>
                <Typography variant="h6">B2B Contracts</Typography>
                <Typography variant="body2">
                    {Object.values(data.companyContractCompanies1).map( (rec:any, index) => {
                        return <Business2BusinessContract key={index} contract={rec}/> 
                    })}
                    {Object.values(data.companyContractCompanies2).map( (rec:any, index) => {
                        return <Business2BusinessContract key={index} contract={rec}/> 
                    })}
                </Typography>
                {/* <B2BContractCreate/> */}
            </div><br/>
            <div>
                <Typography variant="h6">B2P Contracts</Typography>
                <Typography variant="body2">
                    {Object.values(data.companyContractPersons).map( (rec:any, index) => {
                        return <Business2PersonContract key={index} contract={rec}/> 
                    })}
                </Typography>
                {/* <B2PContractCreate/> */}
            </div>
        </div>
    );
}

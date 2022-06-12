import { Button, Typography } from "@mui/material";
import { useRecordContext } from "react-admin";

export const CompanyDetails = () => {
    const data = useRecordContext();
    console.log(data)
    console.log(data.companyContractCompanies1)
    return (
        <div style={{ width: "100%", margin: '1em' }}>
            <div>
                <Typography variant="h6">B2B Contracts</Typography>
                <Typography variant="body2">
                    CompanyId: {data.id}
                    <Button>Add B2B contract</Button>
                </Typography>
            </div><br/>
            <div>
                <Typography variant="h6">B2P Contracts</Typography>
                <Typography variant="body2">
                    CompanyId: {data.id}
                    <Button>Add B2P contract</Button>
                </Typography>
            </div>
        </div>
    );
}
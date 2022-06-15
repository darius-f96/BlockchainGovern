import { Button } from '@mui/material';
import { ArrayField, BooleanField, Datagrid, EditButton, FunctionField, NumberField, NumberInput, Show, SimpleShowLayout, TextField, TopToolbar, useRecordContext, useShowController} from 'react-admin';
import { userCanAcceptCompanyContract, userCanAcceptContract, userModifyCompanyAllowed } from '../../utils/isUserAllowed';

export const CompanyShow = () => {
    const data = useShowController()
    const userAllowed:Boolean = userModifyCompanyAllowed({userRoles:data.record.userRoles})
    console.log(userModifyCompanyAllowed({userRoles:data.record.userRoles}))
    console.log(data)

    const CompanyShowTopToolbar = () =>(
        <TopToolbar>
            {userAllowed && <EditButton />}
        </TopToolbar>
    )
    return (
        <div>
            <Show actions={<CompanyShowTopToolbar/>}>
                <SimpleShowLayout>
                    <TextField source="cui" />
                    <TextField source="description" />
                    <TextField source="name" />
                    <TextField source="regIdentifier" />
                    <TextField label= "Company Contract Companies"/>
                    <ArrayField label = "" source="companyContractCompanies1">
                        <Datagrid
                        isRowSelectable={record => false}
                        >
                            <TextField label="Employer CUI" source="companyId1" />
                            <TextField label = "Contractor CUI" source="companyId2" />
                            <TextField source="terms" />
                            <TextField source="contractCode" />
                            <BooleanField source="accepted" />
                            <FunctionField render={(record:any) => userAllowed && userCanAcceptCompanyContract({companyId:data.record.id, contract:record}) && <Button variant="contained">Accept</Button> } />
                            <FunctionField render={(record:any) => userAllowed && userCanAcceptCompanyContract({companyId:data.record.id, contract:record}) && <Button variant="contained">Decline</Button> } />
                            <FunctionField render={(record:any) => userAllowed && record.accepted && <Button variant="contained" color='error'>End</Button> } />
                        </Datagrid>
                    </ArrayField>
                    <TextField label= "Company Contract Employees"/>
                    <ArrayField label = "" source="companyContractPersons">
                        <Datagrid
                        isRowSelectable={record => false}
                        >
                            <TextField label="Employer CUI" source="companyId" />
                            <TextField label = "Employee" source="appUserId" />
                            <TextField source="terms" />
                            <TextField source="contractCode" />
                            <BooleanField source="accepted" />
                            <NumberField source="contractDetails.amount" label="Amount"/>
                            <FunctionField render={(record:any) => userCanAcceptContract({contract:record}) && <Button variant="contained">Accept</Button> } />
                            <FunctionField render={(record:any) => userCanAcceptContract({contract:record}) && <Button variant="contained">Decline</Button> } />
                            <FunctionField render={(record:any) => userAllowed && record.accepted && <Button variant="contained" color='error'>End</Button> } />
                        </Datagrid>
                    </ArrayField>
                    <TextField source="companyWallets" />

                </SimpleShowLayout>
            </Show>
        </div>
)};
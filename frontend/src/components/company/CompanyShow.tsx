import { Button } from '@mui/material';
import { ArrayField, BooleanField, Datagrid, EditButton, FunctionField, NumberField, NumberInput, Show, SimpleShowLayout, TextField, TopToolbar, useRecordContext, useShowController} from 'react-admin';
import { userCanAcceptCompanyContract, userCanAcceptContract, userCanDeployCompanyContract, userCanDeployContract, userCanEndCompanyContract, userCanEndContract, userModifyCompanyAllowed } from '../../utils/isUserAllowed';
import {CompanyWallet, ContractDetails} from '../../utils/definitions'
import { BigNumber, Contract } from 'ethers';
import { provider, web3 } from '../../utils/provider';
import { useState } from 'react';
import SpringBootRequest from '../../services/SpringBootRequest';
import { DeployContract } from '../web3/build/DeployContract';
import toast from 'react-hot-toast';
import DeployContractModal from '../modal/DeployContractModal';
import AcceptCompanyContractModal from '../modal/AcceptCompanyContractModal';
import AcceptPersonContractModal from '../modal/AcceptPersonContractModal';
export const CompanyShow = () => {
    const data = useShowController()
    const userAllowed:Boolean = userModifyCompanyAllowed({userRoles:data.record.userRoles})
    const [balanceArray, setBalanceArray] = useState<Map<string, string>>(new Map())

    console.log(data)

    const CompanyShowTopToolbar = () =>(
        <TopToolbar>
            {userAllowed && <EditButton />}
        </TopToolbar>
    )
    const GetWalletBalance = async(props:{record:CompanyWallet}) =>{
        const balance = provider.getBalance(props.record.walletId).then((response:BigNumber)=> {
            setBalanceArray(balanceArray.set(props.record.walletId, web3.utils.fromWei(response.toString())))
        })
        return balance
    }

    const declineContract = (props : {contract:any}) => {
        if (!props.contract.companyId2){
            SpringBootRequest(`companyContractPerson/${props.contract.id}`, "DELETE", props.contract).then(response=>{
                console.log(response)
            })
        }
        else {
            SpringBootRequest(`companyContractCompany/${props.contract.id}`, "DELETE", props.contract).then(response=>{
                console.log(response)
            })
        }
    }

    const endContract = (props : {contract:any}) => {
        // props.contract.contractDetails
        if (!props.contract.companyId2){
            SpringBootRequest(`companyContractPerson/${props.contract.id}`, "PUT", props.contract).then(response=>{
                console.log(response)
            })
        }
        else {
            SpringBootRequest(`companyContractCompany/${props.contract.id}`, "PUT", props.contract).then(response=>{
                console.log(response)
            })
        }
    }

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
                            <TextField label= "Contract Address" source="contractId" />
                            <BooleanField source="accepted" />
                            <TextField label="Start Date"  source="contractDetails.startDate" />
                            <TextField label="End Date"  source="contractDetails.endDate" />
                            <TextField label="Amount"  source="contractDetails.amount" />
                            <TextField label="Days before cancel"  source="contractDetails.daysBeforeCancel" />
                            <TextField label="Wire Frequency"  source="contractDetails.wireFrequency" />
                            <TextField label="Last wire "  source="contractDetails.lastWire" />
                            <TextField label="Wire to Address"  source="contractDetails.wireToAddress" />
                            <FunctionField render={(record:any) => 
                                userAllowed && 
                                userCanAcceptCompanyContract({companyId:data.record.cui, contract:record}) && 
                                <AcceptCompanyContractModal data={data.record} contract={record}/> } />
                            <FunctionField render={(record:any) => 
                                userAllowed && 
                                userCanAcceptCompanyContract({companyId:data.record.cui, contract:record}) && 
                                <Button variant="contained" color='error' onClick={()=> declineContract({contract:record})}>Decline</Button> } />
                            <FunctionField render={(record:any) => 
                                (userCanEndCompanyContract({companyId:data.record.cui, contract:record}) || userAllowed) &&
                                record.accepted && 
                                <Button variant="contained" color='error'>End</Button> } />
                            <FunctionField render={(record:any) => 
                                (userCanDeployCompanyContract({companyId:data.record.cui, contract:record}) && userAllowed) &&
                                record.accepted && 
                                !record.contractId &&
                                <DeployContractModal data={data.record} contract={record}/> } />
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
                            <TextField label= "Contract Address" source="contractId" />
                            <BooleanField source="accepted" />
                            <TextField label="Start Date"  source="contractDetails.startDate" />
                            <TextField label="End Date"  source="contractDetails.endDate" />
                            <TextField label="Amount"  source="contractDetails.amount" />
                            <TextField label="Days before cancel"  source="contractDetails.daysBeforeCancel" />
                            <TextField label="Wire Frequency"  source="contractDetails.wireFrequency" />
                            <TextField label="Last wire "  source="contractDetails.lastWire" />
                            <TextField label="Wire to Address"  source="contractDetails.wireToAddress" />
                            <FunctionField render={(record:any) => 
                                userCanAcceptContract({contract:record}) && 
                                <AcceptPersonContractModal data={data.record} contract={record}/> } />
                            <FunctionField render={(record:any) => 
                                userCanAcceptContract({contract:record}) && 
                                <Button variant="contained" color='error' onClick={()=> declineContract({contract:record})}>Decline</Button> } />
                            <FunctionField render={(record:any) => 
                                (userCanEndContract({contract:record}) || userAllowed) && 
                                record.accepted && 
                                <Button variant="contained" color='error'>End</Button> } />
                            <FunctionField render={(record:any) => 
                                (userCanDeployContract({cui:data.record.cui, contract:record}) && userAllowed) &&
                                record.accepted && 
                                !record.contractId &&
                                <DeployContractModal data={data.record} contract={record}/> } />
                        </Datagrid>
                    </ArrayField>
                    <ArrayField label = "" source="companyWallets">
                        <Datagrid
                        isRowSelectable={record => false}
                        >
                            <TextField label="Wallet Code" source="walletCode" />
                            <TextField label = "Description" source="walletDescription" />
                            <TextField label = "Wallet Address" source="walletId" />
                            <FunctionField render={(record:CompanyWallet) =>{GetWalletBalance({record:record}); return <Button variant="outlined">{balanceArray.get(record.walletId)} ETH</Button> }}  />
                        </Datagrid>
                    </ArrayField>

                </SimpleShowLayout>
            </Show>
        </div>
)};
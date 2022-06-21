import { Button } from '@mui/material';
import { ArrayField, BooleanField, Datagrid, EditButton, FunctionField, NumberField, NumberInput, Show, SimpleShowLayout, TextField, TopToolbar, useRecordContext, useShowController} from 'react-admin';
import { userCanAcceptCompanyContract, userCanAcceptContract, userCanDeployCompanyContract, userCanDeployContract, userCanEndCompanyContract, userCanEndContract, userModifyCompanyAllowed } from '../../utils/isUserAllowed';
import {CompanyEntity, CompanyWallet, ContractDetails} from '../../utils/definitions'
import { BigNumber, Contract } from 'ethers';
import { provider, web3 } from '../../utils/provider';
import { useEffect, useState } from 'react';
import SpringBootRequest from '../../services/SpringBootRequest';
import { DeployContract } from '../web3/build/DeployContract';
import toast from 'react-hot-toast';
import DeployContractModal from '../modal/DeployContractModal';
import AcceptCompanyContractModal from '../modal/AcceptCompanyContractModal';
import AcceptPersonContractModal from '../modal/AcceptPersonContractModal';
import DeclineContractModal from '../modal/DeclineContractModal';
import EndContractModal from '../modal/EndContractModal';
import { HandlePayments } from '../web3/HandlePayments';
export const CompanyShow = () => {
    const [balanceArray, setBalanceArray] = useState<Map<string, string>>(new Map())
    const [data, setData] = useState<CompanyEntity>()
    const [userAllowed, setUserAllowed] = useState<Boolean>(false)
 
    useEffect(()=>{
        toast.promise(
            getData(),
             {
               loading: 'Loading...',
               success: <b>Company data loaded!</b>,
               error: <b>Could not load company data.</b>,
             }
           )
    }, [])

    const getData = async () => {
        const companyId = window.location.href.split('/')[5]
        const response = await SpringBootRequest(`company/${companyId}`, 'GET', undefined)
        setData(response)        
        setUserAllowed(userModifyCompanyAllowed({userRoles:response.userRoles}))
    }

    const CompanyShowTopToolbar = () =>(
        <TopToolbar>
            {userAllowed && <HandlePayments companyData={data}/>}
            {userAllowed && <EditButton />}
        </TopToolbar>
    )
    const GetWalletBalance = async(props:{record:CompanyWallet}) =>{
        const balance = provider.getBalance(props.record.walletId).then((response:BigNumber)=> {
            setBalanceArray(balanceArray.set(props.record.walletId, web3.utils.fromWei(response.toString())))
        })
        return balance
    }
    if (!data){
        return (<div></div>)
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
                                userCanAcceptCompanyContract({companyId:data.cui, contract:record}) && 
                                <AcceptCompanyContractModal data={data} contract={record}/> } />
                            <FunctionField render={(record:any) => 
                                userAllowed && 
                                userCanAcceptCompanyContract({companyId:data.cui, contract:record}) && 
                                <DeclineContractModal contract={record}/> } />
                            <FunctionField render={(record:any) => 
                                (userCanEndCompanyContract({companyId:data.cui, contract:record}) || userAllowed) &&
                                record.accepted && 
                                !record.contractDetails.endDate &&
                                <EndContractModal companyData={data} contract={record}/> } />
                            <FunctionField render={(record:any) => 
                                (userCanDeployCompanyContract({companyId:data.cui, contract:record}) && userAllowed) &&
                                record.accepted && 
                                !record.contractId &&
                                <DeployContractModal data={data} contract={record}/> } />
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
                                <AcceptPersonContractModal data={data} contract={record}/> } />
                            <FunctionField render={(record:any) => 
                                userCanAcceptContract({contract:record}) && 
                                <DeclineContractModal contract={record}/> } />
                            <FunctionField render={(record:any) => 
                                (userCanEndContract({contract:record}) || userAllowed) && 
                                record.accepted && 
                                !record.contractDetails.endDate &&
                                <EndContractModal companyData={data} contract={record}/> } />
                            <FunctionField render={(record:any) => 
                                (userCanDeployContract({cui:data.cui, contract:record}) && userAllowed) &&
                                record.accepted && 
                                !record.contractId &&
                                <DeployContractModal data={data} contract={record}/> } />
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
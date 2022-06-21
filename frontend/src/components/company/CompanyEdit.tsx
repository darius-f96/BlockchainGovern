import React, { useEffect, useState } from "react";
import { Edit, required, SimpleForm, TextInput, useListController, useRecordContext, useShowController } from "react-admin";
import toast from "react-hot-toast";
import SpringBootRequest from "../../services/SpringBootRequest";
import { CompanyEntity } from "../../utils/definitions";
import { userModifyCompanyAllowed } from "../../utils/isUserAllowed";
import { B2BContractCreate } from "../contract/B2BContractCreate";
import { B2PContractCreate } from "../contract/B2PContractCreate";
import { CompanyWalletCreate } from "../wallet/CompanyWalletCreate";

export const CompanyEdit = () => {
    const [data, setData] = useState<CompanyEntity>()
    const [userAllowed, setUserAllowed] = useState<Boolean>(false)
    
    console.log(data)
 
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
    if (!data){
        return (<div></div>)
    }
    return (
        <div>
            <Edit>
                <SimpleForm>
                    <TextInput source="name" validate={required()} />
                    <TextInput disabled source="cui" />
                    <TextInput disabled source="regIdentifier" />
                    <TextInput multiline source="description" />
                </SimpleForm>
            </Edit>
            {userAllowed && <B2BContractCreate cui={data.cui}/>}
            {userAllowed && <B2PContractCreate cui={data.cui}/>}
            {userAllowed && <CompanyWalletCreate id={data.id}/>}
        </div>
)};
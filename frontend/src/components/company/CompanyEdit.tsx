import React, { useEffect } from "react";
import { Edit, required, SimpleForm, TextInput, useListController, useRecordContext, useShowController } from "react-admin";
import { userModifyCompanyAllowed } from "../../utils/isUserAllowed";
import { B2BContractCreate } from "../contract/B2BContractCreate";
import { B2PContractCreate } from "../contract/B2PContractCreate";
import { CompanyWalletCreate } from "../wallet/CompanyWalletCreate";

export const CompanyEdit = () => {
    const data = useShowController() 

    const userAllowed:Boolean = userModifyCompanyAllowed({userRoles:data.record.userRoles})
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
            {userAllowed && <B2BContractCreate cui={data.record.cui}/>}
            {userAllowed && <B2PContractCreate cui={data.record.cui}/>}
            {userAllowed && <CompanyWalletCreate id={data.record.id}/>}
        </div>
)};
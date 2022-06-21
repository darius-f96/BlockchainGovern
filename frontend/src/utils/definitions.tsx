import React from 'react';


interface AppUserEntity {
    AppUserId: string;
    Username: string;
    Email: string;
}
export interface PersonEntity {
    id : string;
    AppUserId: string;
    birthDate: Date | null;
    companyId: string;
    firstname : string;
    lastname : string;
    gender : string;
    salutation : string;
    personCode : string;
}
export interface CompanyEntity {
    id : string;
    name: string;
    cui: string;
    description: string;
    regIdentifier: string;
    companyWallets : Array<CompanyWallet>;
    companyContractCompanies1 : Array<B2BContract>;
    companyContractPersons : Array<B2PContract>;
    userRoles : Array<UserRole>
}

export interface B2BContract {
    id : string;
    contractCode : string;
    companyId1 : string;
    companyId2 : string;
    description : string;
    terms : string;
    accepted : boolean;
    contractId : string;
    contractDetails : ContractDetails;
}
export interface B2PContract {
    id : string;
    contractCode : string;
    appUserId : string;
    companyId : string;
    description : string;
    terms : string;
    accepted : boolean;
    contractId : string;
    contractDetails : ContractDetails;
}

export interface UserRole {
    appUserId : string;
    companyId : string;
    id: string;
    roleTypeId : string
}

export interface ContractDetails {
    id : string;
    amount : number;
    startDate : Date|null;
    endDate : Date|null;
    daysBeforeCancel : number;
    active : boolean;
    wireFrequency : number;
    lastWire : Date|null;
    wireToAddress: String;
}
export interface CompanyWallet {
    id : string;
    walletCode : string;
    companyId : string;
    walletDescription : string;
    walletId:string;
}
export interface PersonWallet {
    id : string;
    walletCode : string;
    appUserId : string;
    walletDescription : string;
    walletId:string;
}

export interface AppUserData {
    appusers: Array<AppUserEntity> | undefined;
}
export interface PersonData {
    persons : Array<PersonEntity> | undefined
}
export interface Props {
    children: React.ReactNode;
}
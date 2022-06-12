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
    Name: string;
    CUI: string;
    Description: string;
    regIdentifier: string;
}

export interface B2BContract {
    id : string;
    contractCode : string;
    companyid1 : string;
    companyid2 : string;
    description : string;
}
export interface B2PContract {
    id : string;
    contractCode : string;
    personid : string;
    companyid : string;
    description : string;
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
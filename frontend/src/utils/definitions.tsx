import React from 'react';


interface AppUserEntity {
    AppUserId: string;
    Username: string;
    Email: string;
}
interface PersonEntity {
    PersonId : string;
    AppUserId: string;
    birthDate: Date;
    companyId: string;
    firstname : string;
    lastname : string;
    gender : string;
    salutation : string;
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
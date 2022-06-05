import React from 'react';
import AppUserService from '../services/AppUserService';
import { Admin, Resource, ListGuesser } from 'react-admin';
import SpringBootDataProvider from '../services/SpringBootDataProvider';
import { AppuserList } from './AppUserList';
import { Props, AppUserData, PersonData } from '../utils/definitions';

class ProfileComponent extends React.Component<Props, PersonData>{
    
    mockup : AppUserData = {
        appusers: [
            {AppUserId : 'adad',
            Username: 'test12',
            Email: 'testmail@temp-mail.org'},
            {AppUserId : 'adad',
            Username: 'test12',
            Email: 'testmail@temp-mail.org'},
            {AppUserId : 'adad',
            Username: 'test12',
            Email: 'testmail@temp-mail.org'},
            {AppUserId : 'adad',
            Username: 'test12',
            Email: 'testmail@temp-mail.org'},
            {AppUserId : 'adad',
            Username: 'test12',
            Email: 'testmail@temp-mail.org'},
            {AppUserId : 'adad',
            Username: 'test12',
            Email: 'testmail@temp-mail.org'},
            {AppUserId : 'adad',
            Username: 'test12',
            Email: 'testmail@temp-mail.org'},
            {AppUserId : 'adad',
            Username: 'test12',
            Email: 'testmail@temp-mail.org'}
        ]
    }

    constructor(props : Props){
        super(props)
        this.state = {persons : undefined}
    }

   
    render(): JSX.Element { 
        return (
                <div>
                    worked
                </div>
            
        );
        
    }
}
 
export default ProfileComponent;
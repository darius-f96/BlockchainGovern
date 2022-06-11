import { TextField } from '@mui/material';
import {useEffect, useState} from 'react';
import { Admin, Resource, ListGuesser, Show, SimpleShowLayout, DateField, useDataProvider, ShowBase, Options, Form } from 'react-admin';
import SpringBootDataProvider from '../services/SpringBootDataProvider';
import SpringBootRequest from '../services/SpringBootRequest';
import { convertToObject } from '../utils/convertIntoObject';
import { Props, AppUserData, PersonData, PersonEntity } from '../utils/definitions';
import { ProfileForm } from './ProfileForm';

export const ProfileComponent = () => {

    const [data,setData] = useState([])
    const getData = () => {
        return SpringBootRequest('appUser/userContext', 'GET', {});
    }

    useEffect(() => {
        getData().then(response => { 
            setData(response.persons)
        })
    },[])
    
        

return (
    <div>
        
        {Object.values(data).map( (rec:any, index) => 
            <ProfileForm key={index} rec={rec}/>
        
        )}


    </div>
    
)
}
 
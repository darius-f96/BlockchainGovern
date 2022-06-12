import { Button } from '@mui/material';
import React, {useEffect, useState} from 'react';
import { CreateButton } from 'react-admin';
import { Navigate } from 'react-router-dom';
import SpringBootRequest from '../../services/SpringBootRequest';
import { CreateProfile } from './CreateProfile';
import { Empty } from './EmptyProfile';
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

    const ProfileComp = () => {
        if (data.length === 0) 
            return <div style={{marginTop:200}}><Empty/></div>
            else return( 
                <div>
                    {Object.values(data).map( (rec:any, index) => 
                    <ProfileForm key={index} rec={rec}/>                 
                    )}
                </div>) 
    }
         
return (
    <>
        {<ProfileComp/> }
    </>
    
)
}
 
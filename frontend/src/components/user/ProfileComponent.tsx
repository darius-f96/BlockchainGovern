import { Box, Button } from '@mui/material';
import React, {useEffect, useState} from 'react';

import SpringBootRequest from '../../services/SpringBootRequest';
import { PersonWalletCreate } from '../wallet/PersonWalletCreate';
import { CreateProfile } from './CreateProfile';
import { Empty } from './EmptyProfile';
import { PersonWallets } from './PersonWallets';
import { ProfileForm } from './ProfileForm';

export const ProfileComponent = () => {

    const [data,setData] = useState([])
    const [wallets,setWallets] = useState([])
    const getData = () => {
        return SpringBootRequest('appUser/userContext', 'GET', {});
    }

    useEffect(() => {
        getData().then(response => { 
            setData(response.persons)
            setWallets(response.personWallets)
            console.log("get user data: ", response)
        })
    },[])

    const ProfileComp = () => {
      
        if (data.length === 0) 
            return <div style={{marginTop:100}}><Empty/></div>
            else return( 
                <div style={{marginTop:100}}>
                    {Object.values(data).map( (rec:any, index) =>
                    <div  key={index}>
                        <ProfileForm rec={rec}/>
                        <PersonWalletCreate id={rec.appUserId}/>
                        {<PersonWallets personWallets={wallets}/> }
                    </div>
            )}
                </div>) 
    }
         
return (
    <>
        {<ProfileComp/> }
    </>
    
)
}
 
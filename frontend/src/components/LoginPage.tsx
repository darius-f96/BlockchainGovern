import { Button, TextField } from '@mui/material';
import * as React from 'react';
import { useState } from 'react';
import { useLogin, useNotify, Notification, Form } from 'react-admin';
import RegisterAccountModal from './modal/RegisterAccountModal';

const LoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const login = useLogin();
    const notify = useNotify();

    const style = {
        position: 'absolute' as 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 400,
        bgcolor: 'background.paper',
      };

    const handleSubmit = (e:any) => {
        e.preventDefault();

        login({ username, password }).catch(() =>
            notify('Invalid username or password')
        );
    };

    return (
        <div style={style}>
            <Form>  
                <TextField onChange={(u)=>{setUsername(u.currentTarget.value)}} label="Username" variant='outlined' /><br/>
                <TextField onChange={(p) => {setPassword(p.currentTarget.value)}} label="Password" variant='outlined' type='password' /><br/>
                <Button type="submit" variant="contained" color='success' onClick={handleSubmit}>Sign in</Button>
                <RegisterAccountModal/>
            </Form>
        </div>
    );
};

export default LoginPage;
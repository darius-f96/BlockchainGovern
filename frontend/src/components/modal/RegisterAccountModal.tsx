import * as React from 'react';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import { Form } from 'react-admin';
import { TextField } from '@mui/material';
import SpringBootRequest from '../../services/SpringBootRequest';
import toast from 'react-hot-toast';

const style = {
  position: 'absolute' as 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
};

export default function RegisterAccountModal() {
  const [open, setOpen] = React.useState(false);
  const handleClose = () => {setOpen(false); }
  const [username, setUsername] = React.useState('')
  const [email, setEmail] = React.useState('')
  const [password, setPassword] = React.useState('')
  const [passwordConfirm, setPasswordConfirm] = React.useState('')
  const [emailCheck, setEmailCheck] = React.useState<Boolean>(true);
  const [usernameCheck, setUsernameCheck] = React.useState<Boolean>(true);

  React.useEffect(()=>{
    
  })
  
  const checkEmail = async () =>{
    const response = await SpringBootRequest('appUser/canUseEmail', 'POST', {email : email})
    setEmailCheck(response)
  }

  const checkUsername = async () =>{
    const response = await SpringBootRequest('appUser/canUseUsername', 'POST', {username : username})
    setUsernameCheck(response)
  }

  const signUp = async() => {
    
    const payload ={
      username : username,
      password : password,
      email : email
    }
    const response = await SpringBootRequest('appUser/signup', 'POST', payload)
    if (response){
      toast.success("Your account was registered successfully!")
    }
    else{
      toast.error("There was a problem with registering your account")
    }
    handleClose()
  }

  return (
      <div style ={{display:'inline-block', marginTop:10, marginLeft:5}}>
        <Button variant="contained" onClick={()=> setOpen(true)}>Sign up</Button>
        <Modal
          open={open}
          onClose={handleClose}
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description"
        >
          <Box sx={style}>
            <Typography id="modal-modal-title" variant="h6" component="h2">
                Register account
            </Typography>
            <div style={{justifyContent:"center", display:"flex"}}>       
                    <Form onSubmit={signUp}>
                    <TextField onChange={(u)=>{setUsername(u.currentTarget.value)}} onBlur={checkUsername} label="Username" variant='outlined' /><br/>
                    {!usernameCheck && <p style={{color:'red', fontStyle:'oblique'}}>Username is in use!</p>}
                    <TextField onChange={(u)=>{setEmail(u.currentTarget.value)}} onBlur={checkEmail} label="Email" variant='outlined' /><br/>
                    {!emailCheck && <p style={{color:'red', fontStyle:'oblique'}}>Email is in use!</p>}
                    <TextField onChange={(p) => {setPassword(p.currentTarget.value)}} label="Password" variant='outlined' type='password' /><br/>
                    <TextField onChange={(p) => {setPasswordConfirm(p.currentTarget.value)}} label="Confirm Password" variant='outlined' type='password'/><br/>
                    {password !== passwordConfirm && <p style={{color:'red', fontStyle:'oblique'}}>Passwords do not match!</p>}
                    <Button disabled={username ==='' || password === '' || passwordConfirm ==='' || email === '' || password !== passwordConfirm || !usernameCheck || !emailCheck}
                     type="submit"  color='success' variant="contained" style={{marginTop:10}}>Sign up</Button>
                    <Button type="submit" variant="contained" style={{marginTop:10, marginLeft:10}} color='error' onClick={handleClose}>Cancel</Button>
                </Form>
              </div>
          </Box>
        </Modal>
      </div>
  );
}
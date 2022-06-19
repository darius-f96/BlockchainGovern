import * as React from 'react';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import { Form, TextField } from 'react-admin';
import { FormControl, InputLabel, MenuItem, Select, SelectChangeEvent } from '@mui/material';
import { provider, web3 } from '../../utils/provider';
import toast from 'react-hot-toast';
import { ConnectWallet } from '../web3/ConnectWallet';
import { DeployContract } from '../web3/build/DeployContract';
import SpringBootRequest from '../../services/SpringBootRequest';
import { instanceofBusiness2BusinessContract, instanceofBusiness2PersonContract } from '../../utils/typesCheck';
import { B2BContract, B2PContract } from '../../utils/definitions';

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

export default function DeployContractModal(props: { data:any, contract:B2BContract|B2PContract}) {
  const [open, setOpen] = React.useState(false);
  const [wallet, setWallet] = React.useState<string>('')
  const [deployedAddress, setDeployedAddress] = React.useState('')
  const handleClose = () => {setOpen(false); setWallet(''); setDeployedAddress('')}

  const deploy = async () =>{
    const selectedAccount = await ConnectWallet()

    if (selectedAccount !== wallet){
       toast.error("Your currently connected account is different! Switch and try again after!")
       return
    }
    else {
      DeployContract({fromAccount: wallet, contractToDeploy:props.contract}).then(response=>{
        if (response && response !== ''){
          toast.success("Contract was successfuly deployed and is now active!")
          let payload = props.contract
            payload.contractDetails.active = true
            payload.contractId = response
          if (instanceofBusiness2PersonContract(props.contract)){
            
            SpringBootRequest(`companyContractPerson/${props.contract.id}`, 'PUT', payload).then(response=>{
              if (response){
                toast.success(`Address ${payload.contractId} added to contract information`)}
            })
          }
          else if (instanceofBusiness2BusinessContract(props.contract)){
            SpringBootRequest(`companyContractCompany/${props.contract.id}`, 'PUT', payload).then(response=>{
              if (response){
                toast.success(`Address ${payload.contractId} added to contract information`)}
            })
            
          }
          
        }
      })
    }
    handleClose()
  }
  const handleChangeWallet = (event: SelectChangeEvent) => {
    setWallet(event.target.value)
  }


  return (
      <div>
        <Button variant="contained" color='success' onClick={()=> setOpen(true)}>Deploy Contract</Button>
        <Modal
          open={open}
          onClose={handleClose}
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description"
        >
          <Box sx={style}>
            <div style={{justifyContent:"center", display:"flex"}}>
              <Form onSubmit={deploy}>
                    <FormControl sx={{ m: 1, minWidth: 120 }}>
                    <InputLabel>Select Wallet</InputLabel>
                    <Select
                    value={wallet}
                    label="Wallet"
                    onChange={handleChangeWallet}
                    style={{width:220, backgroundColor:"white"}}
                    >
                    <MenuItem value=''>Select...</MenuItem>
                    { props.data.companyWallets?.map( (rec:any)=>(
                      <MenuItem key={rec.id} value={rec.walletId}>{rec.walletCode}</MenuItem>
                    ))
                    }
                    </Select>
                    </FormControl><br/>
                    <Button disabled={wallet===''} type="submit" color='success' variant="contained" style={{marginTop:10}}>Deploy</Button>
                    <Button type="submit" color='error' variant="contained" style={{marginTop:10, marginLeft:10}}  onClick={handleClose}>Cancel</Button>

                </Form>
              </div>
          </Box>
        </Modal>
      </div>
  );
}
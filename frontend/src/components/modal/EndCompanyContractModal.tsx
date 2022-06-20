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

export default function EndContractModal(props: {contract:any}) {
  const [open, setOpen] = React.useState(false);
  const handleClose = () => {setOpen(false); }

  const endContract = () => {
    
    props.contract.contractDetails.endDate = new Date()
    props.contract.contractDetails.endDate.setDate(props.contract.contractDetails.endDate.getDate() + props.contract.contractDetails.daysBeforeCancel)
    if (instanceofBusiness2PersonContract(props.contract)){
        SpringBootRequest(`companyContractPerson/${props.contract.id}`, "PUT", props.contract).then(response=>{
            if (response)
              toast.success(`Contract period will end on ${props.contract.contractDetails.endDate}`)
            else
              toast.error("Could not set end date for this contract!")
        })
    }
    else {
        SpringBootRequest(`companyContractCompany/${props.contract.id}`, "PUT", props.contract).then(response=>{
          if (response)
            toast.success(`Contract period will end on ${props.contract.contractDetails.endDate}`)
          else 
            toast.error("Could not set end date for this contract!")
        })
    }
    handleClose()
  }

  return (
      <div>
        <Button variant="contained" color="error" onClick={()=> setOpen(true)}>End</Button>
        <Modal
          open={open}
          onClose={handleClose}
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description"
        >
          <Box sx={style}>
            <Typography id="modal-modal-title" variant="h6" component="h2">
                Are you sure?
            </Typography>
            <div style={{justifyContent:"center", display:"flex"}}>       
                    <Button type="submit" variant="contained" color="error" style={{marginTop:10}} onClick={()=>endContract()}>End</Button>
                    <Button type="submit" variant="contained" style={{marginTop:10, marginLeft:10}}  onClick={handleClose}>Cancel</Button>
              </div>
          </Box>
        </Modal>
      </div>
  );
}
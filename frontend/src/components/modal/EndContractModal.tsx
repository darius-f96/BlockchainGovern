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
import { ethers, Signer } from 'ethers';
import WorkContractArtifact from '../../artifacts/contracts/WorkContract.sol/WorkContract.json'
import PayProducts from '../../artifacts/contracts/PayProducts.sol/PayProducts.json'
import BusinessContract from '../../artifacts/contracts/BusinessContract.sol/BusinessContract.json'

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

export default function EndContractModal(props: {companyData:any, contract:any}) {
  const [open, setOpen] = React.useState(false);
  const handleClose = () => {setOpen(false); }
  const [wallet, setWallet] = React.useState<string>('')
  let signer : Signer


  const handleChangeWallet = (event: SelectChangeEvent) => {
    setWallet(event.target.value)
  }

  const endContract = async() => {
    
    const selectedAccount = await ConnectWallet()

    if (wallet !== selectedAccount){
      toast.error("Your connected account does not match the selection")
      return
    }

    signer = provider.getSigner(selectedAccount)
    if (!signer) {
      toast.error("Please connect your wallet!")
      return 
    }

    props.contract.contractDetails.endDate = new Date()
    props.contract.contractDetails.endDate.setDate(props.contract.contractDetails.endDate.getDate() + props.contract.contractDetails.daysBeforeCancel)
    if (instanceofBusiness2PersonContract(props.contract)){
        SpringBootRequest(`companyContractPerson/${props.contract.id}`, "PUT", props.contract).then(response=>{
            if (response){
              let contractHandler = new ethers.Contract(props.contract.contractId, WorkContractArtifact.abi, signer) 
          
              contractHandler.setEndDate().then((response:any)=>{
                  console.log(response)
              })
              toast.success(`Contract period will end on ${props.contract.contractDetails.endDate}`)
            }    
            else
              toast.error("Could not set end date for this contract!")
        })
    }
    else {
        SpringBootRequest(`companyContractCompany/${props.contract.id}`, "PUT", props.contract).then(response=>{
          if (response){
              let contractHandler = new ethers.Contract(props.contract.contractId, BusinessContract.abi, signer) 
          
              contractHandler.setEndDate().then((response:any)=>{
                  console.log(response)
              })
              toast.success(`Contract period will end on ${props.contract.contractDetails.endDate}`)
          } 
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
                Are you sure? Action is irreversible
            </Typography>
            <div style={{justifyContent:"center", display:"flex"}}>       
                    <Form onSubmit={endContract}>
                    <FormControl sx={{ m: 1, minWidth: 120 }}>
                    <InputLabel>Select wallet for payments</InputLabel>
                    <Select
                    value={wallet}
                    label="Wallet"
                    onChange={handleChangeWallet}
                    style={{width:220, backgroundColor:"white"}}
                    >
                    <MenuItem value=''>Select...</MenuItem>
                    { props.companyData.companyWallets?.map( (rec:any)=>(
                      <MenuItem key={rec.id} value={rec.walletId}>{rec.walletCode}</MenuItem>
                    ))
                    }
                    </Select>
                    </FormControl><br/>
                    <Button disabled={wallet===''} type="submit"  color='error' variant="contained" style={{marginTop:10}}>End</Button>
                    <Button type="submit" variant="contained" style={{marginTop:10, marginLeft:10}}  onClick={handleClose}>Cancel</Button>

                </Form>
              </div>
          </Box>
        </Modal>
      </div>
  );
}
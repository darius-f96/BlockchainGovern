import { Contract, ContractFactory, ethers, Signer } from 'ethers';
import WorkContractArtifact from '../../artifacts/contracts/WorkContract.sol/WorkContract.json'
import PayProducts from '../../artifacts/contracts/PayProducts.sol/PayProducts.json'
import BusinessContract from '../../artifacts/contracts/BusinessContract.sol/BusinessContract.json'
import React, { useEffect, useState } from 'react';
import { provider, web3 } from '../../utils/provider';
import toast from 'react-hot-toast';
import { B2BContract, B2PContract } from '../../utils/definitions';
import { dateToSeconds } from '../../utils/dateToSeconds';
import { instanceofBusiness2BusinessContract, instanceofBusiness2PersonContract } from '../../utils/typesCheck';
import { Box, Button, FormControl, InputLabel, MenuItem, Modal, Select, SelectChangeEvent } from '@mui/material';
import { ConnectWallet } from './ConnectWallet';
import { Form } from 'react-admin';
import SpringBootRequest from '../../services/SpringBootRequest';

export const HandlePayments = (props : {companyData:any}) =>{
  const [open, setOpen] = React.useState(false);
  const [wallet, setWallet] = React.useState<string>('')
  const handleClose = () => {setOpen(false); setWallet('')}
  let signer : Signer
  let contractHandler : Contract

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

  const handlePayments = async() =>{

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

    props.companyData.companyContractPersons.map( async(contractPerson:B2PContract) =>{
        if (contractPerson.contractId && contractPerson.contractDetails.active && contractPerson.companyId === props.companyData.cui){
          try {
            contractHandler = new ethers.Contract(contractPerson.contractId, WorkContractArtifact.abi, signer)
            const executed = await contractHandler.wireWage({value: ((contractPerson.contractDetails.amount)*10e17).toString()})
            await executed.wait();
            if (!executed)
              toast.error(`Something went wrong for contract ${contractPerson.contractId}...Payment reverted`)
            else {
              contractPerson.contractDetails.lastWire = new Date()
              SpringBootRequest(`companyContractPerson/${contractPerson.id}`, "PUT", contractPerson)
            }
          } catch (error) {
            console.log("got error during payment process for person contracts: ", error)
          }   
        }
    })
    props.companyData.companyContractCompanies1.map( async(contractCompany:B2BContract) =>{
      if (contractCompany.contractId && contractCompany.contractDetails.active && contractCompany.companyId1 === props.companyData.cui){
        try {
          contractHandler = new ethers.Contract(contractCompany.contractId, BusinessContract.abi, signer) 
          
           const executed = await contractHandler.wireWage({value: ((contractCompany.contractDetails.amount)*10e17).toString()})
           await executed.wait();
          console.log("get balance returned: ", executed)
          if (!executed)
            toast.error(`Something went wrong for contract ${contractCompany.contractId}...Payment reverted`)
          else {
            contractCompany.contractDetails.lastWire = new Date()
            SpringBootRequest(`companyContractCompany/${contractCompany.id}`, "PUT", contractCompany)
          }
        } catch (error) {
          console.log("got error during payment process for company contracts: ", error)
        }   
      }
  })
  handleClose()
  }
  const handleChangeWallet = (event: SelectChangeEvent) => {
    setWallet(event.target.value)
  }

  return (
    
    <div>
        <Button type="submit" color='success' variant="contained" onClick={()=> setOpen(true)}>Trigger payments</Button>
        <Modal
          open={open}
          onClose={handleClose}
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description"
        >
          <Box sx={style}>
            <div style={{justifyContent:"center", display:"flex"}}>
              <Form onSubmit={handlePayments}>
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
                    <Button disabled={wallet===''} type="submit" color='success' variant="contained" style={{marginTop:10}}>Pay</Button>
                    <Button type="submit" color='error' variant="contained" style={{marginTop:10, marginLeft:10}}  onClick={handleClose}>Cancel</Button>

                </Form>
              </div>
          </Box>
        </Modal>
      </div>
  )

}
import { Contract, ContractFactory, ethers, Signer } from 'ethers';
import WorkContractArtifact from '../../../artifacts/contracts/WorkContract.sol/WorkContract.json'
import PayProducts from '../../../artifacts/contracts/PayProducts.sol/PayProducts.json'
import BusinessContract from '../../../artifacts/contracts/BusinessContract.sol/BusinessContract.json'
import { useEffect, useState } from 'react';
import { provider } from '../../../utils/provider';
import toast from 'react-hot-toast';
import { B2BContract, B2PContract } from '../../../utils/definitions';
import { dateToSeconds } from '../../../utils/dateToSeconds';
import { instanceofBusiness2BusinessContract, instanceofBusiness2PersonContract } from '../../../utils/typesCheck';

export const DeployContract = (props : {fromAccount:string, contractToDeploy:B2BContract|B2PContract}) =>{

  let signer : Signer
  let contractAddr =''
  let ContractToDeploy:ContractFactory
  let deployedContract:Contract

   const deployContract = async() =>{

    if (!props.fromAccount){
      toast.error("Invalid deployer account provided, please connect a wallet!")
      return
    }
      signer = provider.getSigner(props.fromAccount)
    if (!props.contractToDeploy || !signer) {
      toast.error("Please connect you wallet!")
      return 
    }

    
    if (instanceofBusiness2PersonContract(props.contractToDeploy)){
      ContractToDeploy = new ethers.ContractFactory(
      WorkContractArtifact.abi,
      WorkContractArtifact.bytecode,
      signer
      )
    }
    else if (instanceofBusiness2BusinessContract(props.contractToDeploy)){
      ContractToDeploy = new ethers.ContractFactory(
        BusinessContract.abi,
        BusinessContract.bytecode,
        signer
      )
    }

  if (!ContractToDeploy) {
    return ""
  }
    try {
      if (instanceofBusiness2PersonContract(props.contractToDeploy)){
        deployedContract = await ContractToDeploy.deploy(
            props.contractToDeploy.contractDetails?.wireToAddress,
            props.contractToDeploy.contractDetails?.amount,
            true,
            props.contractToDeploy.contractDetails?.daysBeforeCancel,
            props.contractToDeploy.contractDetails?.wireFrequency,
            dateToSeconds(props.contractToDeploy.contractDetails.startDate)
        )
      }
      else if (instanceofBusiness2BusinessContract(props.contractToDeploy)){
        deployedContract = await ContractToDeploy.deploy(
          props.contractToDeploy.contractDetails?.wireToAddress,
          props.contractToDeploy.contractDetails?.amount,
          true,
          props.contractToDeploy.contractDetails?.daysBeforeCancel,
          props.contractToDeploy.contractDetails?.wireFrequency,
          dateToSeconds(props.contractToDeploy.contractDetails.startDate),
          true
        )
      }

      await deployedContract.deployed()

      // const greeting = await deployContract.greet()

      console.log(`Contract deployed to: ${deployedContract.address}`)

      contractAddr = deployedContract.address
    } catch (error: any) {
      toast.error("Error occurred during deployment, check your console for details")
      console.log(
        'Error!' + (error && error.message ? `\n\n${error.message}` : '')
      );
    }
    return contractAddr.toLowerCase()
  }

  //  const submitGreeting = async (greeterContract: Contract, greetingInput:string): Promise<void> =>{
  //   try {
  //     const setGreetingTxn = await greeterContract.setGreeting(greetingInput);

  //     await setGreetingTxn.wait();

  //     const newGreeting = await greeterContract.greet();
  //     console.log(`Success!\n\nGreeting is now: ${newGreeting}`);

  //     if (newGreeting !== greeting) {
  //       setGreeting(newGreeting);
  //     }
  //   } catch (error: any) {
  //     console.log(
  //       'Error!' + (error && error.message ? `\n\n${error.message}` : '')
  //     );
  //   }
  // }

  return deployContract()
}
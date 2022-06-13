import { Contract, ethers, Signer } from 'ethers';
import GreeterArtifact from '../../../artifacts/contracts/Greeter.sol/Greeter.json'
import { useEffect, useState } from 'react';
import { provider } from '../../../utils/provider';

export function Greeter() {
 
  const [signer, setSigner] = useState<Signer>(provider.getSigner());
  const [greeterContract, setGreeterContract] = useState<Contract>();
  const [greeterContractAddr, setGreeterContractAddr] = useState<string>('');
  const [greeting, setGreeting] = useState<string>('');
  const [greetingInput, setGreetingInput] = useState<string>('');

  async function deployGreeterContract(signer: Signer): Promise<void> {

    if (greeterContract || !signer) {
      return;
    }

    const Greeter = new ethers.ContractFactory(
      GreeterArtifact.abi,
      GreeterArtifact.bytecode,
      signer
    );

    try {
      const greeterContract = await Greeter.deploy('Hello, Hardhat!');

      await greeterContract.deployed();

      const greeting = await greeterContract.greet();

      setGreeterContract(greeterContract);
      setGreeting(greeting);

      console.log(`Greeter deployed to: ${greeterContract.address}`);

      setGreeterContractAddr(greeterContract.address);
    } catch (error: any) {
      console.log(
        'Error!' + (error && error.message ? `\n\n${error.message}` : '')
      );
    }
  }

  async function submitGreeting(greeterContract: Contract, greetingInput:string): Promise<void> {
    try {
      const setGreetingTxn = await greeterContract.setGreeting(greetingInput);

      await setGreetingTxn.wait();

      const newGreeting = await greeterContract.greet();
      console.log(`Success!\n\nGreeting is now: ${newGreeting}`);

      if (newGreeting !== greeting) {
        setGreeting(newGreeting);
      }
    } catch (error: any) {
      console.log(
        'Error!' + (error && error.message ? `\n\n${error.message}` : '')
      );
    }
  }

  return deployGreeterContract(signer)
}
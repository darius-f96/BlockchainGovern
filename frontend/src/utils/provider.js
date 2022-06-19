
import {ethers} from 'ethers'
import Web3 from 'web3';

export const provider = new ethers.providers.Web3Provider(window.ethereum, "any");
export const web3 = new Web3(window.ethereum);
export const metamask = window.ethereum;
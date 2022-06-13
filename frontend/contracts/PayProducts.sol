//SPDX-License-Identifier: darius-f96
pragma solidity ^0.8.0;

import "hardhat/console.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

contract PayProducts  is Ownable{
    uint private taxModifier;
    address payable private gvt;
    address payable private c_owner;

    constructor() {
        taxModifier = 2000; //state is taking 19% for VAT and 1% from all incoming transfers
        gvt = payable(0xcA908a6C2cC3F3d123be10182A922b0e22edc4D7);
        c_owner = payable(owner());
    }

    function changeOwner (address newOwner) public onlyOwner {
        _transferOwnership(newOwner);
    }

    function payOwner (uint _amount)public returns (bool success) {
        c_owner.transfer(_amount - (_amount*taxModifier/10000));
        gvt.transfer(_amount*taxModifier/10000);
        success = true;
    }
    function setTaxModifier(uint _taxModifier) public onlyOwner{
        taxModifier = _taxModifier;
    }
}

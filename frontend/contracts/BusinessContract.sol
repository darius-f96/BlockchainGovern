//SPDX-License-Identifier: darius-f96
pragma solidity ^0.8.0;

import "hardhat/console.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

contract BusinessContract  is Ownable{
    address payable private contractor;
    address payable private gvt;
    uint256 private wage;
    bool private active;
    bool private vatPayer;
    uint private startDate;
    uint private endDate;
    uint private daysBeforeCancel;
    uint private gvtTaxModifier;
    uint private wireFrequency;
    uint private lastWire;

    constructor(address payable _contractor,
                uint256 _wage, 
                bool _active,
                uint _daysBeforeCancel,
                uint _wireFrequency,
                uint _startDate,
                bool _vatPayer
    )
    public {
        contractor = _contractor;
        wage = _wage;
        vatPayer = _vatPayer;
        startDate = block.timestamp;
        daysBeforeCancel = _daysBeforeCancel;
        active = _active;
        gvt = payable(0xcA908a6C2cC3F3d123be10182A922b0e22edc4D7);
        if (_vatPayer)
            gvtTaxModifier = 2000;
        else 
            gvtTaxModifier = 100;
        startDate = _startDate;
        wireFrequency = _wireFrequency;
    }

    function setInactive() public {
        if (block.timestamp > endDate){
            active = false;
        }
        else {
            revert("Number of necessary days until you can end the contract was not reached.");
        }
        
    }

    function getVatPayer() public view returns (bool) {
        return vatPayer;
    }

    function setVatPayer(bool _vatPayer) public onlyOwner {
        vatPayer = _vatPayer;
    }

    function setEndDate() public {
         if (msg.sender == owner() || msg.sender == contractor)
            endDate = block.timestamp + (daysBeforeCancel * 60 * 60 * 24);
        else
            revert("Access denied.");
        
    }

    function setLastWire(uint _lastWire) public onlyOwner{
        lastWire = _lastWire;
    }

    function setActive() public {
        if (msg.sender == owner() || msg.sender == contractor)
            active = true;
        else
            revert("Access denied.");
    }

    function setWage(uint256 _wage) public onlyOwner{
        wage = _wage;
    }
    function setgvtTaxModifier(uint _taxModifier) public onlyOwner{
        gvtTaxModifier = _taxModifier;
    }

    function wireWage() public onlyOwner returns(bool success) {
        if (startDate < block.timestamp && active && (lastWire + wireFrequency) < block.timestamp){
            console.log("test", wage);
            contractor.transfer(wage - (wage*gvtTaxModifier/10000));
            console.log("after transfer");
            gvt.transfer(wage*gvtTaxModifier/10000);
            setLastWire(block.timestamp);
            success = true;
        }else {
            revert("Contract is no longer active");
        }
        success = false;
    }

    function changeOwner (address newOwner) public onlyOwner {
        _transferOwnership(newOwner);
    }
}

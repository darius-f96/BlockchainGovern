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

    event Received(address, uint);

    receive() external payable {
        emit Received(msg.sender, msg.value);
    }

    error DaysBeforeCancelNotPassed();
    error OnlyOwnerCanCall();
    error ContractNotActiveOrNotStarted();
    error OnlyOwnerAndContractorCanCall();

    function getBalance() external view returns (uint256) {
       return address(this).balance;
     }

    function setInactive() public {
        if (block.timestamp > endDate){
            active = false;
        }
        else {
            revert DaysBeforeCancelNotPassed();
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
            revert OnlyOwnerAndContractorCanCall();
        
    }

    function setLastWire(uint _lastWire) public onlyOwner{
        lastWire = _lastWire;
    }

    function getLastWire() public returns (uint){
        return lastWire;
    }

    function setActive() public {
        if (msg.sender == owner() || msg.sender == contractor)
            active = true;
        else
            revert OnlyOwnerCanCall();
    }

    function setWage(uint256 _wage) public onlyOwner{
        wage = _wage;
    }
    function setgvtTaxModifier(uint _taxModifier) public onlyOwner{
        gvtTaxModifier = _taxModifier;
    }

    function wireWage() public payable onlyOwner returns(bool success) { 
            console.log(endDate);
        if (!(startDate < block.timestamp && active && (lastWire + (wireFrequency * 60 * 60 * 24)) < block.timestamp && endDate < block.timestamp)){
            revert ContractNotActiveOrNotStarted();
        }
        require(msg.value >= wage, "You need to provide more ethereum");
        payable(address(this)).transfer(msg.value);
        contractor.transfer(wage - (wage*gvtTaxModifier/10000));
        console.log(daysBeforeCancel*60*60*24, block.timestamp);
        gvt.transfer(wage*gvtTaxModifier/10000);
        setLastWire(block.timestamp);
        success = true;
    }

    function changeOwner (address newOwner) public onlyOwner {
        _transferOwnership(newOwner);
    }

    function withdraw (address _to) public onlyOwner {
        payable(_to).transfer(address(this).balance);
    }
}

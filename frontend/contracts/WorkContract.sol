//SPDX-License-Identifier: darius-f96
pragma solidity ^0.8.0;

import "hardhat/console.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

contract WorkContract  is Ownable{
    address payable private employee;
    address payable private gvt;
    address payable private health;
    uint256 private wage;
    bool private active;
    uint private startDate;
    uint private endDate;
    uint private daysBeforeCancel;
    uint private gvtTaxModifier;
    uint private healthTaxModifier;
    uint private wireFrequency;
    uint private lastWire;

    constructor(address payable _employee,
                uint256 _wage, 
                bool _active,
                uint _daysBeforeCancel,
                uint _wireFrequency,
                uint _startDate
    )
    public {
        employee = _employee;
        wage = _wage;
        startDate = block.timestamp;
        daysBeforeCancel = _daysBeforeCancel;
        active = _active;
        gvt = payable(0xcA908a6C2cC3F3d123be10182A922b0e22edc4D7);
        health = payable(0x7b4c1ef7e5f8fF1D5d298Bd9ECbcfd72dbfe9E4a);
        gvtTaxModifier = 3279;
        healthTaxModifier = 1000;
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

    function setEndDate() public {
         if (msg.sender == owner() || msg.sender == employee)
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
        if (msg.sender == owner() || msg.sender == employee)
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
    function setHealthTaxModifier(uint _taxModifier) public onlyOwner{
        healthTaxModifier = _taxModifier;
    }

    function wireWage() public payable onlyOwner returns(bool success) {
        if (!(startDate < block.timestamp && active && (lastWire + (wireFrequency * 60 * 60 * 24)) < block.timestamp  && endDate < block.timestamp)){
            revert ContractNotActiveOrNotStarted();
        }

        require(msg.value >= wage, "You need to provide more ethereum");

        payable(address(this)).transfer(msg.value);
        employee.transfer(wage - (wage*gvtTaxModifier/10000) - (wage*healthTaxModifier/10000));
        gvt.transfer(wage*gvtTaxModifier/10000);
        health.transfer(wage*healthTaxModifier/10000);
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

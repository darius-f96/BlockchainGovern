//SPDX-License-Identifier: darius-f96
pragma solidity ^0.8.0;

import "hardhat/console.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

contract WorkContract  is Ownable{
    string private employer;
    address payable private employee;
    address payable private gvt;
    string private code;
    uint256 private wage;
    bool private active;
    uint private startDate;
    uint private daysBeforeCancel;
    uint private taxModifier;

    constructor(string memory _employer, address payable _employee, uint256 _wage, bool _active, string memory _code, uint _daysBeforeCancel) {
        employer = _employer;
        employee = _employee;
        wage = _wage;
        startDate = block.timestamp;
        code = _code;
        daysBeforeCancel = _daysBeforeCancel;
        active = _active;
        gvt = payable(0xcA908a6C2cC3F3d123be10182A922b0e22edc4D7);
        taxModifier = 4279;
    }

    function getCode() public view returns (string memory) {
        return code;
    }

    function setInactive() public {
        if (block.timestamp > startDate + daysBeforeCancel){
            active = false;
        }
        else {
            revert("Number of necessary days until you can end the contract was not reached.");
        }
        
    }

    function setActive() public onlyOwner {
        active = true;
    }

    function setWage(uint256 _wage) public onlyOwner{
        wage = _wage;
    }
    function setTaxModifier(uint _taxModifier) public onlyOwner{
        taxModifier = _taxModifier;
    }

    function wireWage() public onlyOwner returns(bool success) {
        employee.transfer(wage - (wage*taxModifier/10000));
        gvt.transfer(wage*taxModifier/10000);
        success = true;
    }

    function changeOwner (address newOwner) public onlyOwner {
        _transferOwnership(newOwner);
    }
}

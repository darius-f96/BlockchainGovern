const { expect } = require("chai");
const { ethers } = require("hardhat");

describe("BusinessContract", function () {
  it("Should return the new greeting once it's changed", async function () {
    const BusinessContractHandler = await ethers.getContractFactory("BusinessContract");
    const testBusinessContract = await BusinessContractHandler.deploy(
      "0xA5C6Df5652fe691a66ED141B35Fc7D9036EffCfE",
      1,
      true,
      1,
      1,
      1,
      true
    );
    await testBusinessContract.deployed();

    //expect(await testBusinessContract.wire()).to.equal();

    const setWireTx = await testBusinessContract.wireWage();

    // wait until the transaction is mined
    await setWireTx.wait();

  });
});

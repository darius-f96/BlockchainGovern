const { expect } = require("chai");
const { ethers } = require("hardhat");
const provider = ethers.getDefaultProvider();

describe("WorkContract", function () {
  it("Should make the transfer", async function () {
    const BusinessContractHandler = await ethers.getContractFactory("WorkContract");
    const testBusinessContract = await BusinessContractHandler.deploy(
      "0xA5C6Df5652fe691a66ED141B35Fc7D9036EffCfE",
      1,
      true,
      1,
      1,
      1
    );
    await testBusinessContract.deployed();

    //expect(await testBusinessContract.wire()).to.equal();

    const setWireTx = await testBusinessContract.wireWage({
        value: 2000000, // Sends exactly 1.0 ether

    }).then(async function(){
      var balance = await provider.getBalance("0xA5C6Df5652fe691a66ED141B35Fc7D9036EffCfE")
      console.log(balance)
    });

    // wait until the transaction is mined
    //await setWireTx.wait();

  });
});

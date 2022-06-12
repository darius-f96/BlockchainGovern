const TestTestContract = artifacts.require("TestTestContract");

/*
 * uncomment accounts to access the test accounts made available by the
 * Ethereum client
 * See docs: https://www.trufflesuite.com/docs/truffle/testing/writing-tests-in-javascript
 */
contract("TestTestContract", function (/* accounts */) {
  it("should assert true", async function () {
    await TestTestContract.deployed();
    return assert.isTrue(true);
  });
});

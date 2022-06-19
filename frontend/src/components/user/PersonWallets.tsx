import { Button } from "@mui/material";
import {DataGrid, GridColDef, GridRowsProp, GridRenderCellParams } from "@mui/x-data-grid"
import { BigNumber } from "ethers";
import { useState } from "react";
import { provider, web3 } from "../../utils/provider";

export const PersonWallets = (props : { personWallets:GridRowsProp}) => {
    const [balanceArray, setBalanceArray] = useState<Map<string, string>>(new Map())

    const GetWalletBalance = async(props:{walletId:string}) =>{
        const balance = await provider.getBalance(props.walletId).then((response:BigNumber)=> {
            console.log( web3.utils.fromWei(response.toString()))
            setBalanceArray(balanceArray.set(props.walletId, web3.utils.fromWei(response.toString())))
        })
        return balance
    }

    const deleteRow = (id:any) => {
        console.log(id)
    }

      const columns: GridColDef[] = [
        { field: 'walletCode', headerName: 'Wallet Code', width: 100 },
        { field: 'walletDescription', headerName: 'Description', width: 350 },
        { field: 'walletId', headerName: 'Wallet Address', width: 350 },
        {
            field: 'id',
            headerName: 'Balance',
            renderCell: (props: GridRenderCellParams) => {
                GetWalletBalance({walletId:props.row.walletId})
                return(
                <Button variant="outlined">
                  {balanceArray.get(props.row.walletId)} ETH
                </Button>
            )
            },
            width: 150
          },
        { field:'', 
          headerName: 'Actions',
          renderCell: (props: GridRenderCellParams) => {
                return(
                    <Button variant="contained" color="error" onClick={()=>deleteRow(props.row.walletId)}>
                        Delete
                    </Button>
                )
            },
           width:300},
      ];

    return (

            <div style={{ height: 300, width: 1255, marginTop:50}}>
                <DataGrid disableSelectionOnClick hideFooterPagination rows={props.personWallets} columns={columns}/>
            
            </div>

    )
}

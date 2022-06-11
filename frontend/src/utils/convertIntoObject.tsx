
export const convertToObject = (arr:any) => {
    console.log(arr, typeof(arr))
    return arr.reduce( (acc:any, curr:any) => {
        acc[curr.id] = curr
        return acc
    }, {})

}
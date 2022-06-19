

export const dateToSeconds = (strDate: Date|null) => {
    if (!strDate)
        return Math.round(new Date().getTime()/1000)
    return Math.round(new Date(strDate).getTime()/1000)
}
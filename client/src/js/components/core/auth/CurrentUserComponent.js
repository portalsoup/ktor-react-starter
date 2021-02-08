import React from "react";
import { useSelector } from "react-redux"


export const CurrentUserComponent = (props) => {

    const user = useSelector(state => state.currentUser)?.name || "Not logged in"
    
    console.log(`new current user ${user}`)

    return (<label style={{color: "white"}}>{user}</label>)
}
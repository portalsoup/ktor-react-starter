import React from "react";
import { useDispatch } from "react-redux"
import { onLogIn, onLogOut } from "../../../actions/AuthActions";

export const LogoutButtonComponent = () => {
    const dispatch = useDispatch()

    return <button onClick={() => dispatch(onLogOut())}>Log out</button>

}
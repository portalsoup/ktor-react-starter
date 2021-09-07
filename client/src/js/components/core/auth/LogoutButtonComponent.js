import React from "react";
import { useDispatch } from "react-redux"
import { onLogIn, onLogOut } from "../../../actions/AuthActions";

export const LogoutButtonComponent = (props) => {
    const dispatch = useDispatch()

    return <button onClick={() => dispatch(onLogOut())}>Log out {props.name}</button>
}
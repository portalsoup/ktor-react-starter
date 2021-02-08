import React from "react";
import { useDispatch } from "react-redux"
import { onSignUp } from "../../../actions/AuthActions";


export const SignupComponent = (props) => {
    const dispatch = useDispatch()

    const usernameRef = React.createRef()
    const passwordRef = React.createRef()

    const handleSignUp = (e) => {
        e.preventDefault();
        const username = usernameRef.current.value;
        const password = passwordRef.current.value;
        dispatch(onSignUp(username, password))
    }

    return (
        <form onSubmit={handleSignUp.bind(this)}>
            <h3>Sign up reee!</h3>
            <input type="text" ref={usernameRef} placeholder="enter you username" />
            <input type="password" ref={passwordRef} placeholder="enter password" />
            <input type="submit" value="Login" />
        </form>
    )
}
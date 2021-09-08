import React from "react";
import { useDispatch } from "react-redux"
import { onLogIn } from "../../../../actions/AuthActions";

export const LoginComponent = () => {
    const dispatch = useDispatch()

    const usernameRef = React.createRef()
    const passwordRef = React.createRef()

    const handleSignIn = (e) => {
        e.preventDefault();
        const username = usernameRef.current.value;
        const password = passwordRef.current.value;
        dispatch(onLogIn(username, password))
    }

    return (
        <form onSubmit={handleSignIn.bind(this)}>
            <h3>Sign in</h3>
            <input type="text" ref={usernameRef} placeholder="enter you username" />
            <input type="password" ref={passwordRef} placeholder="enter password" />
            <input type="submit" value="Login" />
        </form>
    )
}
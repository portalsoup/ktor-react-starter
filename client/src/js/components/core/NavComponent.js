import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux"
import { Link } from "react-router-dom";

import css from "./NavComponent.css";
import { getCurrentUser } from "../../actions/AuthActions";
import { LogoutButtonComponent } from "./auth/LogoutButtonComponent"

export const NavComponent = () => {
    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(getCurrentUser())
    }, [])

    return <ul className={css.sidenav}>
                    <LoginButton currentUser={useSelector(state => state.currentUser)?.name}></LoginButton>
                    
                    <li>
                        <Link to="/">Home</Link>
                    </li>
                    <li>
                        <Link to="/sign-up">Sign-up</Link>
                    </li>
                </ul>
}

// When logged out, navs to the login page, otherwise is just plain text showing the username
const LoginButton = (props) => {

    console.log(`Current user ${props.currentUser}`)
    const loggedIn = 
    <div>
        <LogoutButtonComponent name={props.currentUser}></LogoutButtonComponent>
    </div>

    const loggedOut = <li><Link to="/sign-in">Login</Link></li>
    
    return props.currentUser ? loggedIn : loggedOut
}
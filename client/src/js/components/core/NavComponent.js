import React from "react";
import { useDispatch, useSelector } from "react-redux"
import { CurrentUserComponent } from "../../components/core/auth/CurrentUserComponent";
import { getPosts } from "../../actions/BlogActions";
import { Link } from "react-router-dom";

import css from "./NavComponent.css";

export const NavComponent = () => {
    const dispatch = useDispatch()
    dispatch(getPosts())

    return <ul className={css.sidenav}>
                    <CurrentUserComponent />
                    <LoginButton></LoginButton>
                    <li>
                        <Link to="/">Home</Link>
                    </li>
                    <li>
                        <Link to="/blog">Blog</Link>
                    </li>
                    <li>
                        <Link to="/sign-in">Sign-in</Link>
                    </li>
                </ul>
}

// When logged out, navs to the login page, otherwise is just plain text showing the username
const LoginButton = () => useSelector(state => state.currentUser)?.name || <li><Link to="/sign-in">Login</Link></li>
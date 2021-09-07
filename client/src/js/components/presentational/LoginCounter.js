import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux"
import { Link } from "react-router-dom";
import { getCurrentUser } from "../../actions/AuthActions";

export const LoginCounter = () => {
    const dispatch = useDispatch()

    useEffect(() => {
        dispatch(getCurrentUser())
    }, [])

    const timesLoggedIn = useSelector(state => state.currentUser.timesLoggedIn)

    const message = timesLoggedIn !== undefined
        ? "You have logged in " + timesLoggedIn + " times"
        : "Not logged in"

    return (<p>{message}</p>)
}
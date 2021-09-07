// import axios from 'axios'; 
import "regenerator-runtime/runtime";
import { CURRENT_USER, NO_CURRENT_USER } from "../constants/action-types";

export function onSignUp(email, password) {
    return async (dispatch) => {
        const response = await fetch(`http://localhost:8080/sign-up`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ email: email, password: password }),
    });
    if (response.ok) {
        console.log("ok?");
    }
}}

export function onLogIn(email, password) {
    return async (dispatch) => {
        const response = await fetch(`http://localhost:8080/sign-in`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({email: email, password: password}),
        credentials: 'include',
    })
    
    if (response.ok) {
        console.log("Success");
    }
    dispatch(getCurrentUser())
}
}

export function onLogOut() {
    return async (dispatch) => {
        const response =  await fetch(`http://localhost:8080/sign-out`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        credentials: "include"
    })
    
    if (response.ok) {
        console.log("Success")
    }
    
    dispatch(getCurrentUser())
    dispatch(getPosts())
}
}

export function getCurrentUser() {
    return async (dispatch) => {
        const response = await fetch(`http://localhost:8080/currentUser`, {
        method: "GET",
        headers: {
        },
        credentials: 'include',
    })
    
    if (response.ok) {
        response.json().then(json => {
            console.log(`About to set state with ${JSON.stringify(json)}`)
            dispatch({
                type: CURRENT_USER,
                payload: json
            });
        })
    } else {
        dispatch({
            type: NO_CURRENT_USER,
            payload: {
                id: undefined,
                name: undefined,
            }
        })
    }
}
}
